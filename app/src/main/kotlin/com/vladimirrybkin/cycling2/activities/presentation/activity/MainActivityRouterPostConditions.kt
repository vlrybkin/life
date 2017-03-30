package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarController
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarLayout
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerEnabled
import rx.Completable

/**
 * Screen transitions postcondition for the main activity life.
 *
 * @author Vladimir Rybkin
 */
class MainActivityRouterPostConditions(val sidemenuOwner: SidemenuOwner,
                                       val toolbarController: ToolbarController,
                                       val inLife: Life,
                                       val containerView: ViewGroup) : Completable.CompletableOnSubscribe {

    override fun call(subsciber: Completable.CompletableSubscriber) {

        val drawerEnabledAnnotation = (inLife as Any).javaClass.getAnnotation(DrawerEnabled::class.java)
        val drawerEnabled = drawerEnabledAnnotation != null && drawerEnabledAnnotation.required
        if (drawerEnabled) {
            sidemenuOwner.unlock()
        }

        val toolbarAnnotation = (inLife as Any).javaClass.getAnnotation(ToolbarLayout::class.java)
        val toolbarViewId = toolbarAnnotation?.toolbarViewId
        if (toolbarViewId != null) {
            val toolbar = containerView.findViewById(toolbarViewId) as Toolbar
            toolbarController.attachToolbar(toolbar)

            if (toolbarAnnotation.toolbarLayoutId > 0) {
                toolbarController.replaceChildViews(toolbarAnnotation.toolbarLayoutId)
            }

            toolbarController.enableHome(toolbarAnnotation.homeAsUp)

            if (toolbarAnnotation.homeIndicator > 0) {
                toolbarController.setHomeIndicator(toolbarAnnotation.homeIndicator)
            }

            if (toolbarAnnotation.titleResId > 0) {
                toolbarController.setTitle(toolbarAnnotation.titleResId)
            }

            if (drawerEnabled) sidemenuOwner.boundToToolbar(true)
            toolbarController.visible(true)
        } else {
            toolbarController.visible(false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            containerView.requestApplyInsets()
        } else {
            @Suppress("DEPRECATION")
            containerView.requestFitSystemWindows()
        }

        subsciber.onCompleted()
    }

}
