package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_BACK_ROUTE
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_DATA
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_REQUEST_CODE
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_RESULT
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_RESULT_CODE
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_SAVED_DATA
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_TRANSITION_IN
import com.vladimirrybkin.lib_router_simple_view.domain.route.RouterState.Companion.EXTRA_TRANSITION_OUT

/**
 * Router state.
 *
 * @author Vladimir Rybkin
 */
data class RouterState(val key: Uri, val data: Bundle?, val savedState: Bundle?,
                       val transitionIn: RouteTransition?,
                       val transitionOut: RouteTransition?,
                       val requestCode: Int, val routeUriBack: Uri?,
                       val resultCode: Int, val result: Bundle?) {

    companion object {
        const val EXTRA_KEY = "SimpleViewUriRouter.EXTRA_KEY"
        const val EXTRA_DATA = "SimpleViewUriRouter.EXTRA_DATA"
        const val EXTRA_TRANSITION_IN = "SimpleViewUriRouter.EXTRA_TRANSITION_IN"
        const val EXTRA_TRANSITION_OUT = "SimpleViewUriRouter.EXTRA_TRANSITION_OUT"
        const val EXTRA_SAVED_DATA = "SimpleViewUriRouter.EXTRA_SAVED_DATA"
        const val EXTRA_REQUEST_CODE = "SimpleViewUriRouter.EXTRA_REQUEST_CODE"
        const val EXTRA_BACK_ROUTE = "SimpleViewUriRouter.EXTRA_BACK_ROUTE"
        const val EXTRA_RESULT_CODE = "SimpleViewUriRouter.EXTRA_RESULT_CODE"
        const val EXTRA_RESULT = "SimpleViewUriRouter.EXTRA_RESULT"
    }

    fun toBundle(out: Bundle, newSavedState: Bundle): Bundle = with(out) {
        putString(EXTRA_KEY, key.toString())
        putBundle(EXTRA_DATA, data)
        putBundle(EXTRA_TRANSITION_IN, transitionIn?.toBundle())
        putBundle(EXTRA_TRANSITION_OUT, transitionOut?.toBundle())
        putBundle(EXTRA_SAVED_DATA, newSavedState)
        putInt(EXTRA_REQUEST_CODE, requestCode)
        putString(EXTRA_BACK_ROUTE, routeUriBack.toString())
        putInt(EXTRA_RESULT_CODE, resultCode)
        putBundle(EXTRA_RESULT, result)
        return out
    }

}

fun Bundle.toRouterState(): RouterState? =
        getString(RouterState.EXTRA_KEY)?.let {
            RouterState(
                    Uri.parse(it),
                    getBundle(EXTRA_DATA),
                    getBundle(EXTRA_SAVED_DATA),
                    getBundle(EXTRA_TRANSITION_IN)?.let(::RouteTransition),
                    getBundle(EXTRA_TRANSITION_OUT)?.let(::RouteTransition),
                    getInt(EXTRA_REQUEST_CODE, -1),
                    getString(EXTRA_BACK_ROUTE)?.let { Uri.parse(it) },
                    getInt(EXTRA_RESULT_CODE, -1),
                    getBundle(EXTRA_RESULT)
            )
        }
