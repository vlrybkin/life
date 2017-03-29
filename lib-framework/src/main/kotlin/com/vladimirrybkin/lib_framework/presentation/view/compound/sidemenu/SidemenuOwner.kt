package com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu

import android.net.Uri
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerContract
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerController
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerStateObservable
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationContract
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationController
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationItemSelectedObservable
import rx.Observable

/**
 * A drawer owner class.
 *
 * @author Vladimir Rybkin
 */
class SidemenuOwner(private val drawerPresenter: DrawerContract.Presenter,
                    private val navigationPresenter: NavigationContract.Presenter) :
        SidemenuObserver,
        SidemenuController,
        DrawerController by drawerPresenter,
        DrawerStateObservable by drawerPresenter,
        NavigationController by navigationPresenter,
        NavigationItemSelectedObservable by navigationPresenter {

    override fun observeMenuItem(): Observable<Uri> =
            navigationPresenter.observeMenuItem()
                    .doOnNext {
                        drawerPresenter.close()
                    }

    fun onBackPressed(): Boolean =
            drawerPresenter.takeIf { drawerPresenter.isOpen() }?.close()?.let { true } ?: false

}
