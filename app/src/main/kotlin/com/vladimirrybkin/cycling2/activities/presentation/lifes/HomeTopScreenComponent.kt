package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.BackRouteUriProvider
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.lib_framework.domain.route.RouteBack
import com.vladimirrybkin.lib_router_simple_view.domain.route.SimpleViewRouterQualifier
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * The home top screen component.
 *
 * @author Vladimir Rybkin
 */
@Subcomponent( modules = arrayOf(HomeTopScreenDI.HomeTopScreenModule::class, HomeTopScreenRoutingModule::class))
interface HomeTopScreenComponent: MembersInjector<HomeTopScreen>

@Module
class HomeTopScreenRoutingModule {

    @Provides @RouteBack("HomeTopScreen")
    fun provideRouteBack(@SimpleViewRouterQualifier backRouteUriProvider: BackRouteUriProvider): UriRoute = with(backRouteUriProvider) {
        UriRoute(getCallingLifeUri())
    }

}
