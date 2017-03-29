package com.vladimirrybkin.cycling2.lib_core.domain.route.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem

/**
 * A router interface.
 *
 * @author Vladimir Rybkin
 */
interface Router<in K> {

    var destroyed: Boolean

    fun push(key: K,
             data: Bundle?,
             savedState: Bundle?,
             transitionIn: RouteTransition?,
             transitionOut: RouteTransition?)

    fun pop(key: K,
            transitionOut: RouteTransition?)

    fun replaceTop(key: K,
                   data: Bundle?,
                   savedState: Bundle?,
                   transitionIn: RouteTransition?,
                   transitionOut: RouteTransition?)

    fun restartStack(key: K,
                     data: Bundle?,
                     savedState: Bundle?,
                     transitionIn: RouteTransition?,
                     transitionOut: RouteTransition?)

    fun save(outState: Bundle): Bundle

    fun restore(inState: Bundle)

    fun backPressed(): Boolean = false

    fun onOptionsItemSelected(item: MenuItem): Boolean = false

    fun onConfigurationChanged(configuration: Configuration) {}

    fun hasLife(): Boolean

    fun destroy() {
        destroyed = true
    }

}
