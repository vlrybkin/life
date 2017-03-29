package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition

/**
 * Router state.
 *
 * @author Vladimir Rybkin
 */
data class RouterState(val key: Uri, val data: Bundle?,
                       val transitionIn: RouteTransition?,
                       val transitionOut: RouteTransition?)
