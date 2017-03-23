package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.support.annotation.IdRes
import android.support.annotation.MenuRes

/**
 * A navigation controller interface.
 *
 * @author Vladimir Rybkin
 */
interface NavigationController {

    fun inflateMenu(@MenuRes resId: Int): Unit?

    fun selectItem(@IdRes id: Int): Unit?

}
