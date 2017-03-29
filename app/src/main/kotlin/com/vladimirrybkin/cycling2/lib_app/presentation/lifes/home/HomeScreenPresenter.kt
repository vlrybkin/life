package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import javax.inject.Inject

/**
 * Home screen presenter class.
 *
 * @author Vladimir Rybkin
 */
class HomeScreenPresenter @Inject constructor(val bootstrapConsumer: BootstrapConsumer) :
        HomeScreenContract.Presenter {

    private var view: HomeScreenContract.View? = null
    private var state = HomeScreenContract.State(0)

    override fun attachView(view: HomeScreenContract.View) {
        this.view = view
        view.renderState(state)
    }

    override fun detachView() {
        this.view = null
    }

    override fun restoreState(savedState: Bundle) {
        state = stateFromBundle(savedState)
        view?.renderState(state)
    }

    override fun saveState(outState: Bundle) {
        stateToBundle(state, outState)
    }

    override fun onClearBootstrapClick() {
        bootstrapConsumer.clearBootstrap()
    }

}
