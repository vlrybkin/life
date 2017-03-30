package com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar

import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.vladimirrybkin.lib_framework.presentation.view.appbar.AppBarController

/**
 * A toolbar presenter class.
 *
 * @author Vladimir Rybkin
 */
class ToolbarPresenter(private val appBarController: AppBarController) :
        ToolbarController, AppBarController by appBarController {

    private var toolbar: Toolbar? = null
    private var bound: Boolean = false

    override fun attachToolbar(toolbar: Toolbar) {
        this.toolbar = toolbar
        appBarController.setToolbarView(toolbar)
    }

    override fun detachToolbar() {
        this.toolbar = null
    }

    override fun findViewById(viewId: Int): View? = toolbar?.findViewById(viewId)

    override fun replaceChildViews(layoutId: Int) {
        removeAllViews()
        inflateChildView(layoutId)
    }

    override fun inflateChildView(layoutId: Int) {
        toolbar.takeIf { toolbar != null }?.apply {
            LayoutInflater.from(context).inflate(layoutId, toolbar, true)
        }
    }

    override fun removeAllViews() {
        toolbar?.removeAllViews()
    }

}
