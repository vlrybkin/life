package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.screen_home_top.view.*

/**
 * The home top screen view.
 *
 * @author Vladimir Rybkin
 */
class HomeTopScreenView : LinearLayout, HomeTopScreenContract.View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var presenter: HomeTopScreenContract.Presenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun applyPresenter(presenter: HomeTopScreenContract.Presenter) {
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

    override fun renderState(state: HomeTopScreenContract.State) {
        home_number.text = state.number.toString()
    }

}
