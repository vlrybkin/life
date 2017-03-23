package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouteTransition
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import rx.Completable

/**
 * A transition executor interface.
 *
 * @author Vladimir Rybkin
 */
interface RouterTransitionExecutor {

    fun createPreTransition(context: Context,
                            keyIn: Uri,
                            savedState: Bundle?,
                            keyOut: Uri?,
                            inLife: Life,
                            inData: Bundle?,
                            outLife: Life?): Completable = Completable.complete()

    fun createLifeTransition(context: Context,
                             containerView: ViewGroup,
                             keyIn: Uri,
                             savedState: Bundle?,
                             transitionIn: RouteTransition?,
                             keyOut: Uri?,
                             transitionOut: RouteTransition?,
                             inLife: Life,
                             inData: Bundle?,
                             outLife: Life?): Completable

    fun createPostTransition(context: Context,
                             keyIn: Uri,
                             savedState: Bundle?,
                             keyOut: Uri?,
                             inLife: Life,
                             inData: Bundle?,
                             outLife: Life?): Completable = Completable.complete()

}
