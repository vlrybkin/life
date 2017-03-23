package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import io.michaelrocks.lightsaber.Module
import javax.inject.Qualifier

/**
 * Home screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface HomeScreenDI {

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class HomeScreenQualifier(val value: String = "")


    @Module
    class HomeScreenModule {

    }

}
