package com.vladimirrybkin.cycling2.activities.presentation.lifes

import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.BackRouteUriProvider
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.lib_framework.domain.route.RouteBack
import com.vladimirrybkin.lib_router_simple_view.domain.route.SimpleViewRouterQualifier
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * The home screen component.
 *
 * @author Vladimir Rybkin
 */
@Subcomponent(modules = arrayOf(SplashScreenDI.SplashScreenModule::class, SplashScreenRoutingModule::class))
interface SplashScreenComponent : MembersInjector<SplashScreen>

@Module class SplashScreenRoutingModule {

    @Provides @RouteBack("SplashScreen")
    fun provideRouteBack(@SplashScreenDI.SplashScreenTargetState targetState: Bundle?,
                         @SplashScreenDI.SplashScreenTargetSavedState targetSavedState: Bundle?,
                         @SimpleViewRouterQualifier backRouteUriProvider: BackRouteUriProvider): UriRoute = with(backRouteUriProvider) {
        UriRoute(getCallingLifeUri()).apply { data(targetState) }
                .apply { savedState(targetSavedState) }
    }

}
