package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.net.Uri
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.toRouterUri

/**
 * URI router action.
 *
 * @author Vladimir Rybkin
 */
fun RouterAction.createRouterUri(scheme: String, authority: String, lifeUri: Uri) : Uri =
    lifeUri.toRouterUri(scheme, authority, actionName())
