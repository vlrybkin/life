package com.vladimirrybkin.lib_framework.presentation.life

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes

@Retention(AnnotationRetention.RUNTIME)
annotation class ParentLayout(@LayoutRes val layoutId: Int, @IdRes val rootViewId: Int = 0)
