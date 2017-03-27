package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.lib_framework.domain.route.RouteBack
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * The home screen component.
 *
 * @author Vladimir Rybkin
 */
@Subcomponent(modules = arrayOf(SplashScreenDI.SplashScreenModule::class, RoutingModule::class))
interface SplashScreenComponent: MembersInjector<SplashScreen>

@Module
class RoutingModule {

    @Provides @RouteBack
    fun provideRouteBack(@CollapseScreenDI.CollapseScreenQualifier route: UriRoute): UriRoute = route

}
