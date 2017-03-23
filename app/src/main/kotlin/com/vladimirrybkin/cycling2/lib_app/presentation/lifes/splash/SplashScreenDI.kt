package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash

import io.michaelrocks.lightsaber.Module
import javax.inject.Qualifier

/**
 * Splash screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface SplashScreenDI {

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class SplashScreenQualifier(val value: String = "")

    @Module
    class SplashScreenModule {

    }

}
