package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.net.Uri
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarController
import rx.Completable

/**
 * Lives transitions precondition for the main activity life.
 *
 * @author Vladimir Rybkin
 */
class MainActivityRouterPreConditions(val keyIn: Uri,
                                      val sidemenuOwner: SidemenuOwner,
                                      val toolbarController: ToolbarController) :
        Completable.CompletableOnSubscribe {

    override fun call(subsciber: Completable.CompletableSubscriber) {
        sidemenuOwner.lock()
        sidemenuOwner.selectItem(keyIn)
        sidemenuOwner.boundToToolbar(false)
        toolbarController.detachToolbar()
        subsciber.onCompleted()
    }

}
