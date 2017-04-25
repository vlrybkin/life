package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.content.Context
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenContract
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.homeTopStateToBundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import javax.inject.Inject

/**
 * Home screen presenter class.
 *
 * @author Vladimir Rybkin
 */
class HomeScreenPresenter @Inject constructor(private val context: Context,
                                              private val bootstrapConsumer: BootstrapConsumer,
                                              @HomeTopScreenDI.HomeTopScreenQualifier private val topScreenRoute: UriRoute) :
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
        state = homeStateFromBundle(savedState)
        view?.renderState(state)
    }

    override fun saveState(outState: Bundle) {
        homeStateToBundle(state, outState)
    }

    override fun onClearBootstrapClick() {
        bootstrapConsumer.clearBootstrap()
    }

    override fun onSendToTopScreen() {
        topScreenRoute.requestCode(1)
                .data(homeTopStateToBundle(HomeTopScreenContract.State(state.number + 1), Bundle()))
                .go(context)
    }

}
