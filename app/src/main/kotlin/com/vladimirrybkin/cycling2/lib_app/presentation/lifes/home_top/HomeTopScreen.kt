package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKey
import com.vladimirrybkin.lib_framework.domain.di.life.DILife
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarLayout
import dagger.MembersInjector
import javax.inject.Inject

/**
 * A screen displayed on top of the home.
 *
 * @author Vladimir Rybkin
 */
@LifeKey(value = "/screen/home_top")
@BootstrapRequired(true)
@ParentLayout(layoutId = R.layout.screen_home_top)
@ToolbarLayout(toolbarViewId = R.id.toolbar)
class HomeTopScreen(val injector: (HomeTopScreenDI.HomeTopScreenModule) -> MembersInjector<HomeTopScreen>) : DILife() {

    @Inject
    lateinit var presenter: HomeTopScreenContract.Presenter

    override fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {
        super.onCreateView(parentViewGroup, inState)

        if (inState == null) throw RuntimeException("no state")

        injector(HomeTopScreenDI.HomeTopScreenModule(contextWrapper as Context, inState)).injectMembers(this)
        (parentViewGroup as HomeTopScreenContract.View).applyPresenter(presenter)
    }

    override fun onRestoreState(savedState: Bundle) {
        super.onRestoreState(savedState)
        presenter.restoreState(savedState)
    }

    override fun onSaveState(outState: Bundle) {
        super.onSaveState(outState)
        presenter.saveState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            return presenter.onBack()
        else
            return super.onOptionsItemSelected(item)
    }

}
