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

    private var currentState: RouterState? = null
    private var currentLife: Life? = null
    private var currentTransition: Subscription = Subscriptions.unsubscribed()
    private var nextState: RouterState? = null

    companion object {
        private const val EXTRA_KEY = "SimpleViewUriRouter.EXTRA_KEY"
        private const val EXTRA_DATA = "SimpleViewUriRouter.EXTRA_DATA"
        private const val EXTRA_TRANSITION_IN = "SimpleViewUriRouter.EXTRA_TRANSITION_IN"
        private const val EXTRA_TRANSITION_OUT = "SimpleViewUriRouter.EXTRA_TRANSITION_OUT"
        private const val EXTRA_SAVED_DATA = "SimpleViewUriRouter.EXTRA_SAVED_DATA"
    }

    override fun push(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?,
                      transitionOut: RouteTransition?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, savedState, transitionIn, transitionOut)
    }

    override fun pop(key: Uri,
                     data: Bundle?,
                     savedState: Bundle?,
                     transitionIn: RouteTransition?,
                     transitionOut: RouteTransition?) {
        // stack is not supported, do nothing
    }

    override fun replaceTop(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?,
                            transitionOut: RouteTransition?) =
            setup(key, data = data, savedState = savedState,
                    transitionIn = transitionIn, transitionOut = transitionOut, restored = false)

    private fun setup(key: Uri, data: Bundle?, savedState: Bundle?,
                      transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                      restored: Boolean) {
        if (destroyed) return

        if (currentTransition.isUnsubscribed) {
            val executor = getExecutor(key, currentState?.key, transitionIn, transitionOut, restored) ?:
                    defaultExecutor

            val inLife = produceLife(key, data) ?:
                    throw IllegalStateException("The router cannot create a lifecycle for the key " + key)
            val outLife = currentLife

            currentTransition = Completable.concat(
                    executor.createPreTransition(containerView.context,
                            key,
                            currentState?.key,
                            inLife, data, savedState, outLife),
                    executor.createLifeTransition(containerView.context,
                            containerView,
                            key, transitionIn,
                            currentState?.key, transitionOut,
                            inLife, data, savedState, outLife),
                    executor.createPostTransition(containerView.context,
                            containerView,
                            key,
                            currentState?.key,
                            inLife, data, savedState, outLife)
            )
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted {
                        currentLife = inLife
                        currentState = RouterState(key, data, transitionIn, transitionOut)
                    }
                    .doOnUnsubscribe {
                        startNextIfRequired()
                    }
                    .subscribe({ currentTransition.unsubscribe() },
                            { currentTransition.unsubscribe() })
        } else
            nextState = RouterState(key, data, transitionIn, transitionOut)
    }

    private fun startNextIfRequired() {
        val localNextState = nextState
        if (localNextState != null) {
            val localNextKey = localNextState.key
            val localNextData = localNextState.data
            val localNextTransitionIn = localNextState.transitionIn
            val localNextTransitionOut = localNextState.transitionOut
            nextState = null
            setup(localNextKey, localNextData, null, localNextTransitionIn,
                    localNextTransitionOut, false)
        }
    }

    override fun restartStack(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?,
                              transitionOut: RouteTransition?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, savedState, transitionIn, transitionOut)
    }

    override fun save(outState: Bundle): Bundle {
        val localState = currentState
        val localLife = currentLife
        if (localState != null && localLife != null) {
            val savedLifeState = Bundle()
            localLife.onSaveState(savedLifeState)

            outState.putString(EXTRA_KEY, localState.key.toString())
            outState.putBundle(EXTRA_DATA, localState.data)
            outState.putBundle(EXTRA_TRANSITION_IN, localState.transitionIn?.toBundle())
            outState.putBundle(EXTRA_TRANSITION_OUT, localState.transitionOut?.toBundle())
            outState.putBundle(EXTRA_SAVED_DATA, savedLifeState)
        }

        return outState
    }

    override fun restore(inState: Bundle) {
        val lifeKey = inState.getString(EXTRA_KEY)
        if (lifeKey != null) {
            val lifeKeyUri = Uri.parse(lifeKey)
            val data = inState.getBundle(EXTRA_DATA)
            val transitionIn: RouteTransition? = inState.getBundle(EXTRA_TRANSITION_IN)?.let(::RouteTransition)
            val transitionOut: RouteTransition? = inState.getBundle(EXTRA_TRANSITION_OUT)?.let(::RouteTransition)
            val savedState = inState.getBundle(EXTRA_SAVED_DATA)
            setup(lifeKeyUri, data, savedState, transitionIn, transitionOut, restored = true)
        }
    }

    override fun destroy() {
        super.destroy()
        currentTransition.unsubscribe()
    }

    override fun hasLife(): Boolean = currentState != null || !currentTransition.isUnsubscribed

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
