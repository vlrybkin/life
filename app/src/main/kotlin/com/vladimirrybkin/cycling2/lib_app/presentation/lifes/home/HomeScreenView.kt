package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.screen_home.view.*
import org.jetbrains.anko.onClick

/**
 * The home screen view.
 *
 * @author Vladimir Rybkin
 */
class HomeScreenView : LinearLayout, HomeScreenContract.View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var presenter: HomeScreenContract.Presenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        home_clear_bootstrap.onClick { presenter?.onClearBootstrapClick() }
    }

    override fun applyPresenter(presenter: HomeScreenContract.Presenter) {
        this.presenter = presenter
        if (isAttachedToWindow) presenter.attachView(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter?.attachView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter?.detachView()
    }

    override fun renderState(state: HomeScreenContract.State) {
        home_number.text = state.number.toString()
    }

}
