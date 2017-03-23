package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.activities.presentation.lifes.CollapseScreenComponent
import com.vladimirrybkin.cycling2.activities.presentation.lifes.HomeScreenComponent
import com.vladimirrybkin.cycling2.activities.presentation.lifes.SplashScreenComponent
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_app.domain.preconditions.LifePreconditions
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouter
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.cycling2.lib_core.presentation.life.uri.UriLifeProducer
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuController
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuObserver
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerContract
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerPresenter
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerViewWrapper
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationContract
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationPresenter
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationViewWrapper
import com.vladimirrybkin.lib_router_simple_view.domain.route.DefaultRouterExecutor
import com.vladimirrybkin.lib_router_simple_view.domain.route.SimpleViewUriRouter
import io.michaelrocks.lightsaber.*
import rx.Completable
import rx.Subscriber
import javax.inject.Singleton

/**
 * Activity life cycle DI classes.
 *
 * @author Vladimir Rybkin
 */
class MainActivityLifeDI {

    companion object {

        const val COMPONENT_NAME = "MainActivityLifeDI"

    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class InitialRoute

    @Component(parents = arrayOf(MainActivityDI.MainActivityComponent::class))
    class MainActivityLifeComponent(val lifeModule: MainActivityLifeModule,
                                    val routeModule: MainActivityRouteModule) {

        @Provides
        fun provideMainActivityLifeModule(): MainActivityLifeModule {
            return lifeModule
        }

        @Provides
        fun provideMainActivityRouteModule(): MainActivityRouteModule {
            return routeModule
        }

    }

    @Module
    class MainActivityLifeModule(val drawerLayout: DrawerLayout,
                                 val navigationView: NavigationView) {

        @Provides
        fun provideSidemenuObserver(sidemenuOwner: SidemenuOwner): SidemenuObserver = sidemenuOwner

        @Provides
        fun provideSidemenuController(sidemenuOwner: SidemenuOwner): SidemenuController = sidemenuOwner

        @Provides @Singleton
        fun provideSidemenuOwner(drawerPresenter: DrawerContract.Presenter,
                                 navigationPresenter: NavigationContract.Presenter):
                SidemenuOwner = SidemenuOwner(drawerPresenter, navigationPresenter)

        @Provides @Singleton
        fun provideDrawerPresenter(activity: Activity): DrawerContract.Presenter {
            val drawerPresenter = DrawerPresenter()
            DrawerViewWrapper(activity, drawerLayout, drawerPresenter)
            return drawerPresenter
        }

        @Provides @Singleton
        fun provideNavigationPresenter(): NavigationContract.Presenter {
            val navigationPresenter = NavigationPresenter(R.menu.sidebar_menu)
            NavigationViewWrapper(navigationView, navigationPresenter)
            return navigationPresenter
        }

        @Provides @Singleton
        fun provideSidemenuSubscriber(
                @HomeScreenDI.HomeScreenQualifier homeRoute: UriRoute,
                @CollapseScreenDI.CollapseScreenQualifier collapseRoute: UriRoute
        ): Subscriber<MenuItem> = object : Subscriber<MenuItem>() {

            override fun onError(e: Throwable?) = Unit

            override fun onNext(item: MenuItem) = when (item.itemId) {
                R.id.sidebar_home -> homeRoute.go()
                R.id.sidebar_collapse_recycler -> collapseRoute.go()
                else -> Unit
            }

            override fun onCompleted() = Unit
        }
    }

    @Module
    class MainActivityRouteModule(val context: Context,
                                  val screenContainer: ViewGroup) {

        @Provides
        @Singleton
        fun provideSimpleViewRoute(bootstrapProvider: BootstrapProvider,
                                   @SplashScreenDI.SplashScreenQualifier splashScreenKey: Uri,
                                   @SplashScreenDI.SplashScreenQualifier splashScreenProducer: UriLifeProducer,
                                   @HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                                   @HomeScreenDI.HomeScreenQualifier homeScreenProducer: UriLifeProducer,
                                   @CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri,
                                   @CollapseScreenDI.CollapseScreenQualifier collapseProducer: UriLifeProducer): UriRouter =
                SimpleViewUriRouter(screenContainer)
                        .also {
                            it.replaceDefaultExecutor(object : DefaultRouterExecutor() {
                                override fun createPreTransition(
                                        context: Context,
                                        keyIn: Uri, savedState: Bundle?,
                                        keyOut: Uri?,
                                        inLife: Life, inData: Bundle?,
                                        outLife: Life?): Completable {

                                    return Completable.create(LifePreconditions(
                                            (inLife as Any).javaClass,
                                            bootstrapProvider,
                                            provideSplashScreenRoute(splashScreenKey, it)))
                                }

                                override fun onRootFrameReady(inLife: Life, rootFrame: ViewGroup): ViewGroup {
                                    val parentLayoutAnnotation = (inLife as Any).javaClass.getAnnotation(ParentLayout::class.java)

                                    if (parentLayoutAnnotation == null) {
                                        return super.onRootFrameReady(inLife, rootFrame)
                                    } else {
                                        val context = rootFrame.context
                                        val wrapView = LayoutInflater.from(context).inflate(
                                                parentLayoutAnnotation.layoutId, rootFrame, false) as ViewGroup
                                        val lifeView = if (parentLayoutAnnotation.rootViewId > 0)
                                            wrapView.findViewById(parentLayoutAnnotation.rootViewId) as ViewGroup
                                        else
                                            wrapView
                                        rootFrame.addView(wrapView)
                                        return lifeView
                                    }
                                }

                            })
                        }
                        .apply {
                            addProducer(splashScreenKey, splashScreenProducer)
                        }
                        .apply {
                            addProducer(homeScreenKey, homeScreenProducer)
                        }
                        .apply {
                            addProducer(collapseScreenKey, collapseProducer)
                        }

        @Provides
        @InitialRoute
        fun provideInitialRoute(@HomeScreenDI.HomeScreenQualifier homeScreenRoute: UriRoute): UriRoute
                = homeScreenRoute

        @Provides
        @Singleton
        @HomeScreenDI.HomeScreenQualifier
        fun provideHomeScreenLifeProducers(): UriLifeProducer {
            val activityLifeInjector = context.getSystemService(COMPONENT_NAME) as Injector
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return HomeScreen({
                        Lightsaber.get().createChildInjector(activityLifeInjector,
                                HomeScreenComponent())
                    })
                }
            }
        }

        @Provides
        @HomeScreenDI.HomeScreenQualifier
        fun provideHomeScreenRoute(@HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                                   router: UriRouter): UriRoute =
                UriRoute(homeScreenKey, router::replaceTop)

        @Provides
        @Singleton
        @CollapseScreenDI.CollapseScreenQualifier
        fun provideCollapseScreenLifeProducers(): UriLifeProducer {
            val activityLifeInjector = context.getSystemService(COMPONENT_NAME) as Injector
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return CollapseScreen({
                        Lightsaber.get().createChildInjector(activityLifeInjector,
                                CollapseScreenComponent())
                    })
                }
            }
        }

        @Provides
        @CollapseScreenDI.CollapseScreenQualifier
        fun provideCollapseScreenRoute(@CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri,
                                       router: UriRouter): UriRoute =
                UriRoute(collapseScreenKey, router::replaceTop)

        @Provides
        @Singleton
        @SplashScreenDI.SplashScreenQualifier
        fun provideSplashScreenLifeProducers(): UriLifeProducer {
            val activityLifeInjector = context.getSystemService(COMPONENT_NAME) as Injector
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return SplashScreen({
                        Lightsaber.get().createChildInjector(activityLifeInjector,
                                SplashScreenComponent())
                    })
                }
            }
        }

        @Provides
        @SplashScreenDI.SplashScreenQualifier
        fun provideSplashScreenRoute(@SplashScreenDI.SplashScreenQualifier splashScreenKey: Uri,
                                     router: UriRouter): UriRoute =
                UriRoute(splashScreenKey, router::replaceTop)

    }
}
