package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.*
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
class SimpleViewUriRouter(authority: String,
                          private val containerView: ViewGroup) : UriRouter(authority), BackRouteUriProvider {

    private var transitionExecutors: MutableList<Pair<UriRouterTransitionRule, RouterTransitionExecutor>>
            = mutableListOf()

    private var defaultExecutor: RouterTransitionExecutor = DefaultRouterExecutor()

    private var currentState: RouterState? = null
    private var currentLife: Life? = null
    private var currentTransition: Subscription = Subscriptions.unsubscribed()
    private var nextState: RouterState? = null

    companion object {

        const val NO_ACTION = "_no_screen"

    }

    override fun createRoute(lifeUri: Uri, action: RouterAction): UriRoute =
            UriRoute(action.createRouterUri("", authority, lifeUri))

    override fun push(key: Uri, data: Bundle?, savedState: Bundle?,
                      transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                      requestCode: Int, routeUriBack: Uri?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, savedState, transitionIn, transitionOut, requestCode, routeUriBack,
                -1, null)
    }

    override fun pop(key: Uri,
                     transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                     resultCode: Int, result: Bundle?) {
        // stack is not supported, do nothing
    }

    override fun replaceTop(key: Uri, data: Bundle?, savedState: Bundle?,
                            transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                            requestCode: Int, routeUriBack: Uri?,
                            resultCode: Int, result: Bundle?) =
            setup(RouterState(key, data, savedState, transitionIn, transitionOut, requestCode, routeUriBack, resultCode, result),
                    restored = false)

    override fun restartStack(key: Uri, data: Bundle?, savedState: Bundle?,
                              transitionIn: RouteTransition?, transitionOut: RouteTransition?,
                              requestCode: Int, routeUriBack: Uri?,
                              resultCode: Int, result: Bundle?) {
        // stack is not supported, just forward the call
        replaceTop(key, data, savedState, transitionIn, transitionOut, requestCode, routeUriBack,
                resultCode, result)
    }

    private fun setup(state: RouterState, restored: Boolean) {
        if (destroyed || state.key.path == NO_ACTION) return

        if (currentTransition.isUnsubscribed) {
            val lifeUri = state.key.toLifeUri()

            val executor = getExecutor(lifeUri, currentState?.key, state.transitionIn, state.transitionOut, restored) ?:
                    defaultExecutor

            val inLife = produceLife(lifeUri, state.data) ?:
                    throw IllegalStateException("The router cannot create a lifecycle for the key " + lifeUri)
            val outLife = currentLife

            currentTransition = Completable.concat(
                    executor.createPreTransition(containerView.context,
                            state, inLife, currentState, outLife),
                    executor.createLifeTransition(containerView.context,
                            containerView,
                            state, inLife, currentState, outLife),
                    executor.createPostTransition(containerView.context,
                            containerView,
                            state, inLife, currentState, outLife)
            )
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted {
                        currentLife = inLife
                        currentState = state
                    }
                    .doOnUnsubscribe {
                        startNextIfRequired()
                    }
                    .subscribe({ currentTransition.unsubscribe() },
                            { currentTransition.unsubscribe() })
        } else
            nextState = state
    }

    private fun startNextIfRequired() {
        nextState?.let {
            nextState = null
            setup(it, restored = false)
        }
    }

    override fun save(outState: Bundle): Bundle {
        currentState?.let {
            val lifeSave = Bundle()
            currentLife?.onSaveState(lifeSave)
            it.toBundle(outState, lifeSave)
        }
        return outState
    }

    override fun restore(inState: Bundle) {
        inState.toRouterState()?.let {
            setup(it, restored = true)
        }
    }

    override fun destroy() {
        super.destroy()
        currentTransition.unsubscribe()
    }

    override fun backPressed(): Boolean =
            currentLife?.onBackPressed() ?: false || super.backPressed()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            currentLife?.onOptionsItemSelected(item) ?: false || super.onOptionsItemSelected(item)

    override fun getCallingLifeUri(): Uri = currentState?.requestCode?.takeIf { it >= 0 }
            ?.let { currentState?.routeUriBack } ?: Uri.Builder().authority(authority).path(NO_ACTION).build()

}
