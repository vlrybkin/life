package com.vladimirrybkin.lib_framework.presentation.view.appbar

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar

/**
 * App bar contract
 *
 * @author Vladimir Rybkin
 */
interface AppBarController {

    fun visible(value: Boolean)

    fun setToolbarView(toolbarView: Toolbar?)

    fun enableHome(enabled: Boolean)

    fun setHomeIndicator(@DrawableRes indicatorResId: Int?)

    fun setTitle(@StringRes titleResId: Int)

    fun setTitle(title: CharSequence)

}
