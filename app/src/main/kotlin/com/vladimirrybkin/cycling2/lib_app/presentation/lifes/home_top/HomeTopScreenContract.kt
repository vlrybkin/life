package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top

import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenContract.State.Companion.EXTRA_NUMBER

/**
 * A home top screen contract.
 *
 * @author Vladimir Rybkin
 */
interface HomeTopScreenContract {

    interface View {

        fun applyPresenter(presenter: Presenter)

        fun renderState(state: State)

    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun restoreState(savedState: Bundle)

        fun saveState(outState: Bundle)

        fun onBack(): Boolean

    }

    data class State(val number: Int) {

        companion object {
            const val EXTRA_NUMBER = "HomeTopScreenContract.State.EXTRA_NUMBER"
        }

    }

}

fun homeTopStateToBundle(state: HomeTopScreenContract.State, outBundle: Bundle): Bundle {
    outBundle.putInt(EXTRA_NUMBER, state.number + 1)
    return outBundle
}

fun homeTopStateFromBundle(inBundle: Bundle): HomeTopScreenContract.State =
        HomeTopScreenContract.State(inBundle.getInt(EXTRA_NUMBER))
