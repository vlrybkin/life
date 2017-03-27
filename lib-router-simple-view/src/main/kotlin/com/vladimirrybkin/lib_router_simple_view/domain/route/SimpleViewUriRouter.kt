package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouter
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouterTransitionRule
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import rx.Completable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions

/**
 * A simple view-based URI router class.
 *
 * @author Vladimir Rybkin
 */
open class SimpleViewUriRouter(val containerView: ViewGroup) : UriRouter() {

    private var transitionExecutors: MutableList<Pair<UriRouterTransitionRule, RouterTransitionExecutor>>
            = mutableListOf()

    private var defaultExecutor: RouterTransitionExecutor = DefaultRouterExecutor()

    private var currentLife: Life? = null
    private var currentKey: Uri? = null
    private var currentTransition: Subscription = Subscriptions.unsubscribed()

    private var nextKey: Uri? = null
    private var nextData: Bundle? = null
    private var nextTransitionIn: RouteTransition? = null
    private var nextTransitionOut: RouteTransition? = null

    override fun push(key: Uri, data: Bundle?, transitionIn: RouteTransition?,
                      transitionOut: RouteTransition?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, transitionIn, transitionOut)
    }

    override fun pop(key: Uri, data: Bundle?, transitionIn: RouteTransition?,
                     transitionOut: RouteTransition?) {
        // stack is not supported, do nothing
    }

    override fun replaceTop(key: Uri, data: Bundle?, transitionIn: RouteTransition?,
                            transitionOut: RouteTransition?) =
            setup(key, data = data, savedState = null,
                    transitionIn = transitionIn, transitionOut = transitionOut, restored = false)

    private fun setup(key: Uri, data: Bundle?, savedState: Bundle?,
                      transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                      restored: Boolean) {
        if (destroyed) return

        if (currentTransition.isUnsubscribed) {
            val executor = getExecutor(key, currentKey, transitionIn, transitionOut, restored) ?:
                    defaultExecutor

            val inLife = produceLife(key, data) ?:
                    throw IllegalStateException("The router cannot create a lifecycle for the key " + key)
            val outLife = currentLife

            currentTransition = Completable.concat(
                    executor.createPreTransition(containerView.context,
                            key,
                            currentKey,
                            inLife, data, savedState, outLife),
                    executor.createLifeTransition(containerView.context,
                            containerView,
                            key, transitionIn,
                            currentKey, transitionOut,
                            inLife, data, savedState, outLife),
                    executor.createPostTransition(containerView.context,
                            key,
                            currentKey,
                            inLife, data, savedState, outLife)
            )
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted {
                        currentLife = inLife
                        currentKey = key
                    }
                    .doOnUnsubscribe {
                        startNextIfRequired()
                    }
                    .subscribe({ currentTransition.unsubscribe() },
                            { currentTransition.unsubscribe() })
        } else {
            nextKey = key
            nextData = data
            nextTransitionIn = transitionIn
            nextTransitionOut = nextTransitionOut
        }
    }

    private fun startNextIfRequired() {
        val localNextKey = nextKey
        if (localNextKey != null) {
            val localNextData = nextData
            val localNextTransitionIn = nextTransitionIn
            val localNextTransitionOut = nextTransitionOut

            nextKey = null
            nextData = null
            nextTransitionIn = null
            nextTransitionOut = null

            setup(localNextKey, localNextData, null, localNextTransitionIn,
                    localNextTransitionOut, false)
        }
    }

    override fun restartStack(key: Uri, data: Bundle?, transitionIn: RouteTransition?,
                              transitionOut: RouteTransition?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, transitionIn, transitionOut)
    }

    override fun save(outState: Bundle): Bundle {
        // TODO
        return outState
    }

    override fun restore(inState: Bundle) {
        // TODO
    }

    override fun destroy() {
        super.destroy()
        currentTransition.unsubscribe()
    }

    override fun hasLife(): Boolean = currentKey != null || !currentTransition.isUnsubscribed

    fun addTransitionHandler(rule: UriRouterTransitionRule, executor: RouterTransitionExecutor)
            = transitionExecutors.add(Pair(rule, executor))

    fun replaceDefaultExecutor(defaultExecutor: RouterTransitionExecutor) {
        this.defaultExecutor = defaultExecutor
    }

    private fun getExecutor(keyIn: Uri, keyOut: Uri?,
                            transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                            restored: Boolean)
            : RouterTransitionExecutor? =
            transitionExecutors.firstOrNull {
                it.first.isApplicable(keyIn, keyOut, transitionIn, transitionOut, restored)
            }?.second

}
