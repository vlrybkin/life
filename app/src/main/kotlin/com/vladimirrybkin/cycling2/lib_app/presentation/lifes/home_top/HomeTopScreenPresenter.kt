package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top

import android.content.Context
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute

/**
 * Home top screen presenter class.
 *
 * @author Vladimir Rybkin
 */
class HomeTopScreenPresenter(private val context: Context, inState: Bundle, val routeBack: UriRoute) :
        HomeTopScreenContract.Presenter {

    private var view: HomeTopScreenContract.View? = null
    private var state = HomeTopScreenContract.State(0)

    init {
        state = homeTopStateFromBundle(inState)
    }

    override fun attachView(view: HomeTopScreenContract.View) {
        this.view = view
        view.renderState(state)
    }

    override fun detachView() {
        this.view = null
    }

    override fun restoreState(savedState: Bundle) {
        state = homeTopStateFromBundle(savedState)
        view?.renderState(state)
    }

    override fun saveState(outState: Bundle) {
        homeTopStateToBundle(state, outState)
    }

    override fun onBack(): Boolean {
        //routeBack.backChannel()?.back(1, homeTopStateToBundle(state.copy(number = state.number + 1), Bundle()))
        //routeBack.go()

        return true
    }

}
