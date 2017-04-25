package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.net.Uri
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction

/**
 * URI extensitions.
 *
 * @author Vladimir Rybkin
 */
fun Uri.toRouterUri(scheme: String, authority: String, action: String) =
        Uri.Builder().scheme(scheme).authority(authority).path(path).appendQueryParameter("action", action).build()

fun Uri.toLifeUri() = Uri.parse(path)

fun Uri.getRouterAction() = when (getQueryParameter("action")) {
    RouterAction.ACTION_PUSH.actionName() -> RouterAction.ACTION_PUSH
    RouterAction.ACTION_REPLACE_TOP.actionName() -> RouterAction.ACTION_REPLACE_TOP
    RouterAction.ACTION_POP.actionName() -> RouterAction.ACTION_POP
    RouterAction.ACTION_RESTART_STACK.actionName() -> RouterAction.ACTION_RESTART_STACK
    else -> throw IllegalArgumentException("Unexpected router uri " + this)
}
