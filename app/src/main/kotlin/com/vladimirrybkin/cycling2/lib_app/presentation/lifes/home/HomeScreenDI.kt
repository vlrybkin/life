package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import android.content.Context
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

/**
 * Home screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface HomeScreenDI {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class HomeScreenQualifier(val value: String = "")

    @Module
    class HomeScreenModule(private val context: Context) {

        @Provides
        fun providePresenter(bootstrapConsumer: BootstrapConsumer,
                             @HomeTopScreenDI.HomeTopScreenQualifier topScreenRoute: UriRoute): HomeScreenContract.Presenter =
            HomeScreenPresenter(context, bootstrapConsumer, topScreenRoute)
    }

}
