package com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar

import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes

@Retention(AnnotationRetention.RUNTIME)
annotation class ToolbarLayout(@IdRes val toolbarViewId: Int,
                               @LayoutRes val toolbarLayoutId: Int = -1,
                               @StringRes val titleResId: Int = -1,
                               val homeAsUp: Boolean = true,
                               @DrawableRes val homeIndicator: Int = -1)
