package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

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
    class HomeScreenModule {

        @Provides
        fun providePresenter(presenter: HomeScreenPresenter): HomeScreenContract.Presenter = presenter

    }

}
