package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreenContract.State.Companion.EXTRA_NUMBER

/**
 * A home screen contract.
 *
 * @author Vladimir Rybkin
 */
interface HomeScreenContract {

    interface View {

        fun applyPresenter(presenter: Presenter)

        fun renderState(state: State)

    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun restoreState(savedState: Bundle)

        fun saveState(outState: Bundle)

        fun onClearBootstrapClick()

        fun onSendToTopScreen()

    }

    data class State(val number: Int) {

        companion object {
            const val EXTRA_NUMBER = "HomeScreenContract.State.EXTRA_NUMBER"
        }

    }

}

fun homeStateToBundle(state: HomeScreenContract.State, outBundle: Bundle): Bundle {
    outBundle.putInt(EXTRA_NUMBER, state.number + 1)
    return outBundle
}

fun homeStateFromBundle(inBundle: Bundle): HomeScreenContract.State =
        HomeScreenContract.State(inBundle.getInt(EXTRA_NUMBER))
