package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash

import dagger.Module
import javax.inject.Qualifier

/**
 * Splash screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface SplashScreenDI {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SplashScreenQualifier(val value: String = "")

    @Module
    class SplashScreenModule {

    }

}
