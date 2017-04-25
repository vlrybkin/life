package com.vladimirrybkin.lib_router_activity.domain.route

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouter

/**
 * A router to launch/close lifes in activities.
 *
 * @author Vladimir Rybkin
 */
class ActivityRouter(private val activity: Activity) : UriRouter() {
    override fun push(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?, transitionOut: RouteTransition?, requestCode: Int, routeUriBack: Uri?) {
        // TODO
    }

    override fun createRoute(lifeUri: Uri, action: RouterAction): UriRoute {
        // TODO
        return UriRoute(Uri.parse(""))
    }

    override fun pop(key: Uri, transitionIn: RouteTransition?, transitionOut: RouteTransition?, resultCode: Int, result: Bundle?) {
        // TODO
    }

    override fun replaceTop(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?, transitionOut: RouteTransition?, requestCode: Int, routeUriBack: Uri?, resultCode: Int, result: Bundle?) {
        // TODO
    }

    override fun restartStack(key: Uri, data: Bundle?, savedState: Bundle?, transitionIn: RouteTransition?, transitionOut: RouteTransition?, requestCode: Int, routeUriBack: Uri?, resultCode: Int, result: Bundle?) {
        // TODO
    }

    override fun save(outState: Bundle): Bundle {
        // TODO
        return outState
    }

    override fun restore(inState: Bundle) {
        // TODO
    }

    override fun hasLife(): Boolean {
        // TODO
        return false
    }


}
