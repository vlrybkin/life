package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.Route
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction

class UriRoute(private var key: Uri) : Route {

    private var data: Bundle? = null
    private var savedState: Bundle? = null
    private var transitionIn: RouteTransition? = null
    private var transitionOut: RouteTransition? = null
    private var requestCode: Int = -1
    private var routeUriBack: Uri? = null
    private var resultCode: Int = -1
    private var result: Bundle? = null

    companion object {
        const val EXTRA_KEY = "UriRoute.EXTRA_KEY"
        const val EXTRA_DATA = "UriRoute.EXTRA_DATA"
        const val EXTRA_TRANSITION_IN = "UriRoute.EXTRA_TRANSITION_IN"
        const val EXTRA_TRANSITION_OUT = "UriRoute.EXTRA_TRANSITION_OUT"
        const val EXTRA_SAVED_DATA = "UriRoute.EXTRA_SAVED_DATA"
        const val EXTRA_REQUEST_CODE = "UriRoute.EXTRA_REQUEST_CODE"
        const val EXTRA_BACK_ROUTE = "UriRoute.EXTRA_BACK_ROUTE"
        const val EXTRA_RESULT_CODE = "UriRoute.EXTRA_RESULT_CODE"
        const val EXTRA_RESULT = "UriRoute.EXTRA_RESULT"
    }

    constructor(bundle: Bundle) : this(Uri.parse(bundle.getString(EXTRA_KEY))) {
        data = bundle.getBundle(EXTRA_DATA)
        savedState = bundle.getBundle(EXTRA_SAVED_DATA)
        transitionIn = bundle.getBundle(EXTRA_TRANSITION_IN)?.let(::RouteTransition)
        transitionOut = bundle.getBundle(EXTRA_TRANSITION_OUT)?.let(::RouteTransition)
        requestCode = bundle.getInt(EXTRA_REQUEST_CODE, -1)
        routeUriBack = bundle.getString(EXTRA_BACK_ROUTE)?.let { Uri.parse(it) }
        resultCode =  bundle.getInt(EXTRA_RESULT_CODE, -1)
        result = bundle.getBundle(EXTRA_RESULT)
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

    override fun go(context: Context) {
        val router: UriRouter = context.getSystemService(key.authority) as UriRouter? ?:
                throw RuntimeException("A router is not found for the authority " + key.authority)
        when (key.getRouterAction()) {
            RouterAction.ACTION_PUSH -> router.push(
                    key,
                    data,
                    savedState,
                    transitionIn,
                    transitionOut,
                    requestCode,
                    routeUriBack
            )

            RouterAction.ACTION_POP -> router.pop(
                    key,
                    transitionIn,
                    transitionOut,
                    resultCode,
                    result
            )

            RouterAction.ACTION_REPLACE_TOP -> router.replaceTop(
                    key,
                    data,
                    savedState,
                    transitionIn,
                    transitionOut,
                    requestCode,
                    routeUriBack,
                    resultCode,
                    result
            )

            RouterAction.ACTION_RESTART_STACK -> router.restartStack(
                    key,
                    data,
                    savedState,
                    transitionIn,
                    transitionOut,
                    requestCode,
                    routeUriBack,
                    resultCode,
                    result
            )
        }
    }

    fun data(data: Bundle?): UriRoute {
        this.data = data
        return this
    }

    fun requestCode(code: Int): UriRoute {
        this.requestCode = code
        return this
    }

    fun backRoute(routeUriBack: Uri): UriRoute {
        this.routeUriBack = routeUriBack
        return this
    }

    fun result(resultCode: Int, result: Bundle?): UriRoute {
        this.requestCode = resultCode
        this.result = result
        return this
    }

    fun savedState(savedState: Bundle?): UriRoute {
        this.savedState = savedState
        return this
    }

    fun transitionIn(transitionIn: RouteTransition?): UriRoute {
        this.transitionIn = transitionIn
        return this
    }

    fun transitionOut(transitionOut: RouteTransition?): UriRoute {
        this.transitionOut = transitionOut
        return this
    }

}
