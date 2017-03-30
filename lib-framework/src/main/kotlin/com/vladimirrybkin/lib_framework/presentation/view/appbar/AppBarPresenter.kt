package com.vladimirrybkin.lib_framework.presentation.view.appbar

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

/**
 * App bar presenter class.
 *
 * @author Vladimir Rybkin
 */
class AppBarPresenter(private val activity: AppCompatActivity) : AppBarController {

    override fun visible(value: Boolean) {
        activity.supportActionBar?.let { if (value) it.show() else it.hide() }
    }

    override fun setToolbarView(toolbarView: Toolbar?) = activity.setSupportActionBar(toolbarView)

    override fun enableHome(enabled: Boolean) {
        activity.supportActionBar?.setHomeButtonEnabled(enabled)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
    }

    override fun setHomeIndicator(indicatorResId: Int?) {
        activity.supportActionBar?.setHomeAsUpIndicator(indicatorResId ?: 0)
    }

    override fun setTitle(titleResId: Int) {
        activity.supportActionBar?.setTitle(titleResId)
    }

    override fun setTitle(title: CharSequence) {
        activity.supportActionBar?.title = title
    }

}
