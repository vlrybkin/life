package com.vladimirrybkin.cycling2.activities.presentation.activity

import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerEnabled
import rx.Completable

/**
 * Screen transitions postcondition for the main activity life.
 *
 * @author Vladimir Rybkin
 */
class MainActivityRouterPostconditions(val sidemenuOwner: SidemenuOwner,
                                       val inLife: Life) : Completable.CompletableOnSubscribe {

    override fun call(subsciber: Completable.CompletableSubscriber) {
        val drawerEnabledAnnotation = (inLife as Any).javaClass.getAnnotation(DrawerEnabled::class.java)
        val drawerEnabled = drawerEnabledAnnotation != null && drawerEnabledAnnotation.required
        if (drawerEnabled) {
            sidemenuOwner.unlock()
        }
        subsciber.onCompleted()
    }

}
