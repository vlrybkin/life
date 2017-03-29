package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.net.Uri
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import rx.Completable

/**
 * Screen transitions precondition for the main activity life.
 *
 * @author Vladimir Rybkin
 */
class MainActivityRouterPreconditions(val keyIn: Uri,
                                      val sidemenuOwner: SidemenuOwner) : Completable.CompletableOnSubscribe {

    override fun call(subsciber: Completable.CompletableSubscriber) {
        sidemenuOwner.lock()
        sidemenuOwner.selectItem(keyIn)
        subsciber.onCompleted()
    }

}
