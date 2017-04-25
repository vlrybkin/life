package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.net.Uri

/**
 * Uri provider of the calling life.
 *
 * @author Vladimir Rybkin
 */
interface BackRouteUriProvider {

    fun getCallingLifeUri(): Uri

}
