package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKey
import com.vladimirrybkin.lib_framework.domain.di.life.DILife
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarLayout
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerEnabled
import dagger.MembersInjector
import javax.inject.Inject

/**
 * The home screen life.
 *
 * @author Vladimir Rybkin
 */
@LifeKey(value = "/screen/home")
@BootstrapRequired(true)
@ParentLayout(layoutId = R.layout.screen_home)
@ToolbarLayout(toolbarViewId = R.id.toolbar, toolbarLayoutId = R.layout.toolbar_home, titleResId = R.string.home_title)
@DrawerEnabled
class HomeScreen(val injector: (HomeScreenDI.HomeScreenModule) -> MembersInjector<HomeScreen>) : DILife() {

    @Inject
    lateinit var presenter: HomeScreenContract.Presenter

    override fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {
        super.onCreateView(parentViewGroup, inState)
        injector(HomeScreenDI.HomeScreenModule(contextWrapper as Context)).injectMembers(this)
        (parentViewGroup as HomeScreenContract.View).applyPresenter(presenter)
    }

    override fun onRestoreState(savedState: Bundle) {
        super.onRestoreState(savedState)
        presenter.restoreState(savedState)
    }

    override fun onSaveState(outState: Bundle) {
        super.onSaveState(outState)
        presenter.saveState(outState)
    }

}
