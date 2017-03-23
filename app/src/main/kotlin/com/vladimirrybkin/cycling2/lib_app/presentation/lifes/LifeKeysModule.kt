package com.vladimirrybkin.cycling2.lib_app.presentation.lifes

import android.net.Uri
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import io.michaelrocks.lightsaber.Module
import io.michaelrocks.lightsaber.Provides

/**
 * Life cycles keys.
 *
 * @author Vladimir Rybkin
 */
@Module
class LifeKeysModule {

    @SplashScreenDI.SplashScreenQualifier
    @Provides
    fun provideSplashKey(): Uri = Uri.parse(getKey(SplashScreen::class.java))

    @HomeScreenDI.HomeScreenQualifier
    @Provides
    fun provideHomeScreenKey(): Uri = Uri.parse(getKey(HomeScreen::class.java))

    @CollapseScreenDI.CollapseScreenQualifier
    @Provides
    fun provideCollapseScreenKey(): Uri = Uri.parse(getKey(CollapseScreen::class.java))

}
