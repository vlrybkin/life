package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top

import android.content.Context
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.lib_framework.domain.route.RouteBack
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

/**
 * Home screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface HomeTopScreenDI {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class HomeTopScreenQualifier(val value: String = "")

    @Module
    class HomeTopScreenModule(private val context: Context, private val inState: Bundle) {

        @Provides
        fun providePresenter(@RouteBack("HomeTopScreen") routeBack: UriRoute): HomeTopScreenContract.Presenter =
                HomeTopScreenPresenter(context, inState, routeBack)

    }

}
