package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.net.Uri

/**
 * A navigation controller interface.
 *
 * @author Vladimir Rybkin
 */
interface NavigationController {

    fun selectItem(key: Uri)

}
