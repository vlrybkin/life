package com.vladimirrybkin.cycling2.lib_core.domain.route.base

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Menu
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
             transitionOut: RouteTransition?,
             requestCode: Int,
             routeUriBack: Uri?)

    fun pop(key: K,
            transitionIn: RouteTransition?,
            transitionOut: RouteTransition?,
            resultCode: Int,
            result: Bundle?)

    fun replaceTop(key: K,
                   data: Bundle?,
                   savedState: Bundle?,
                   transitionIn: RouteTransition?,
                   transitionOut: RouteTransition?,
                   requestCode: Int,
                   routeUriBack: Uri?,
                   resultCode: Int,
                   result: Bundle?)

    fun restartStack(key: K,
                     data: Bundle?,
                     savedState: Bundle?,
                     transitionIn: RouteTransition?,
                     transitionOut: RouteTransition?,
                     requestCode: Int,
                     routeUriBack: Uri?,
                     resultCode: Int,
                     result: Bundle?)

    fun save(outState: Bundle): Bundle

    fun restore(inState: Bundle)

    fun backPressed(): Boolean = false

    fun onCreateOptionsMenu(menu: Menu): Boolean = false

    fun onPrepareOptionsMenu(menu: Menu): Boolean = false

    fun onOptionsItemSelected(item: MenuItem): Boolean = false

    fun onConfigurationChanged(configuration: Configuration) {}

    fun hasLife(): Boolean

    fun destroy() {
        destroyed = true
    }

}
