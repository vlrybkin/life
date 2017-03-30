package com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import android.view.View
import com.vladimirrybkin.lib_framework.presentation.view.appbar.AppBarController

/**
 * A toolbar controller interface.
 *
 * @author Vladimir Rybkin
 */
interface ToolbarController: AppBarController {

    fun attachToolbar(toolbar: Toolbar)

    fun detachToolbar()

    fun findViewById(@IdRes viewId: Int): View?

    fun replaceChildViews(@LayoutRes layoutId: Int)

    fun inflateChildView(@LayoutRes layoutId: Int)

    fun removeAllViews()

}
