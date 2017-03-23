package com.vladimirrybkin.lib_framework.presentation.view.navigation

/**
 * Navigation view/presenter contract.
 *
 * @author Vladimir Rybkin
 */
interface NavigationContract {

    interface View : NavigationItemSelectedObservable, NavigationController {

        fun inflated(): Boolean
    }

    interface Presenter : NavigationItemSelectedObservable, NavigationController {

        fun attachView(view: View)

        fun detachView()

    }

}
