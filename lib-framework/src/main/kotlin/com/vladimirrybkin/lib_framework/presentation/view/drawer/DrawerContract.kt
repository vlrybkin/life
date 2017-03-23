package com.vladimirrybkin.lib_framework.presentation.view.drawer

/**
 * A drawer view/presenter interface.
 *
 * @author Vladimir Rybkin
 */
interface DrawerContract {

    interface View : DrawerStateObservable, DrawerController

    interface Presenter : DrawerStateObservable, DrawerController {

        fun attachView(view: View)

        fun detachView()

    }

}
