package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import io.michaelrocks.lightsaber.Component
import io.michaelrocks.lightsaber.Provides

/**
 * The home screen component.
 *
 * @author Vladimir Rybkin
 */
@Component
class SplashScreenComponent {

    @Provides
    fun splashScreenModule(): SplashScreenDI.SplashScreenModule = SplashScreenDI.SplashScreenModule()

}
