package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.support.annotation.IdRes
import android.support.annotation.MenuRes
import android.view.MenuItem
import rx.Observable

/**
 * Navigation view/presenter contract.
 *
 * @author Vladimir Rybkin
 */
interface NavigationContract {

    interface View {

        fun inflated(): Boolean

        fun observeMenuItem(): Observable<MenuItem>

        fun inflateMenu(@MenuRes resId: Int)

        fun selectItem(@IdRes id: Int)
    }

    interface Presenter : NavigationItemSelectedObservable, NavigationController {

        fun attachView(view: View)

        fun detachView()

    }

}
