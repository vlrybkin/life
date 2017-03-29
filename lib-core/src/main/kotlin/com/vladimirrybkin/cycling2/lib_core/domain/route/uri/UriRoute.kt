package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.Route
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition

class UriRoute(private var key: Uri,
               private val exec: (key: Uri,
                                  data: Bundle?,
                                  savedState: Bundle?,
                                  transitionIn: RouteTransition?,
                                  transitionOut: RouteTransition?) -> Unit) : Route {

    private var inState: Bundle? = null
    private var savedState: Bundle? = null
    private var transitionIn: RouteTransition? = null
    private var transitionOut: RouteTransition? = null

    override fun go() = exec(key, inState, savedState, transitionIn, transitionOut)

    fun scheme(scheme: String): Route {
        key = key.buildUpon().scheme(scheme).build()
        return this
    }

    fun authority(authority: String): Route {
        key = key.buildUpon().authority(authority).build()
        return this
    }

    fun inState(inState: Bundle?): Route {
        this.inState = inState
        return this
    }

    fun savedState(savedState: Bundle?): Route {
        this.savedState = savedState
        return this
    }

    fun transitionIn(transitionIn: RouteTransition?): Route {
        this.transitionIn = transitionIn
        return this
    }

    fun transitionOut(transitionOut: RouteTransition?): Route {
        this.transitionOut = transitionOut
        return this
    }

}
