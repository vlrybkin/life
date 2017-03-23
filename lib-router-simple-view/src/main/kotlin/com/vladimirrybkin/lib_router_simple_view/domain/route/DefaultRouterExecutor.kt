package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_router_simple_view.R
import rx.Completable

/**
 * The default router transition executor.
 *
 * @author Vladimir Rybkin
 */
open class DefaultRouterExecutor : RouterTransitionExecutor {

    override fun createLifeTransition(context: Context, containerView: ViewGroup,
                                      keyIn: Uri, savedState: Bundle?, transitionIn: RouteTransition?,
                                      keyOut: Uri?, transitionOut: RouteTransition?,
                                      inLife: Life, inData: Bundle?,
                                      outLife: Life?): Completable {
        return Completable.create {
            tearDown(outLife, containerView)
            setup(context, containerView, inLife, inData, savedState)
            it.onCompleted()
        }
    }

    private fun setup(context: Context, containerView: ViewGroup,
                      inLife: Life?, inData: Bundle?, savedState: Bundle?) {
        if (inLife == null) return

        inLife.attachBaseContext(context)
        inLife.onCreate(inData)

        val rootFrame = LayoutInflater.from(context)
                .inflate(R.layout.view_router_root, containerView, false) as ViewGroup
        val targetView = onRootFrameReady(inLife, rootFrame)
        inLife.onCreateView(targetView, inData)
        containerView.addView(rootFrame)

        if (savedState != null) inLife.onRestoreState(savedState)
        inLife.onStart()
    }

    open fun onRootFrameReady(inLife: Life, rootFrame: ViewGroup): ViewGroup = rootFrame

    private fun tearDown(outLife: Life?, containerView: ViewGroup) {
        if (outLife == null) return

        outLife.onStop()
        outLife.onDestroyView()
        containerView.removeAllViews()
        outLife.onDestroy()
        outLife.detachBaseContext()
        containerView.removeAllViews()
    }

}
