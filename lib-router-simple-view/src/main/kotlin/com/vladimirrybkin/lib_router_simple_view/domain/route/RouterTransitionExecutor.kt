package com.vladimirrybkin.lib_router_simple_view.domain.route

import android.content.Context
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import rx.Completable

/**
 * A transition executor interface.
 *
 * @author Vladimir Rybkin
 */
interface RouterTransitionExecutor {

    fun createPreTransition(context: Context,
                            stateIn: RouterState,
                            lifeIn: Life,
                            stateOut: RouterState?,
                            lifeOut: Life?): Completable = Completable.complete()

    fun createLifeTransition(context: Context,
                             containerView: ViewGroup,
                             stateIn: RouterState,
                             lifeIn: Life,
                             stateOut: RouterState?,
                             lifeOut: Life?): Completable

    fun createPostTransition(context: Context,
                             containerView: ViewGroup,
                             stateIn: RouterState,
                             lifeIn: Life,
                             stateOut: RouterState?,
                             lifeOut: Life?): Completable = Completable.complete()

}
