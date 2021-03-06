package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.activities.presentation.lifes.*
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_app.domain.preconditions.LifePreconditions
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home_top.HomeTopScreenDI
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash.SplashScreenDI
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.RouterAction
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.BackRouteUriProvider
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouter
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.cycling2.lib_core.presentation.life.uri.UriLifeProducer
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuController
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuObserver
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarController
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerContract
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerPresenter
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerViewWrapper
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationContract
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationPresenter
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationViewWrapper
import com.vladimirrybkin.lib_router_simple_view.domain.route.DefaultRouterExecutor
import com.vladimirrybkin.lib_router_simple_view.domain.route.SimpleViewRouterQualifier
import com.vladimirrybkin.lib_router_simple_view.domain.route.SimpleViewUriRouter
import com.vladimirrybkin.lib_router_simple_view.domain.route.createRouterUri
import dagger.MembersInjector
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import rx.Completable
import rx.Subscriber
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Activity life cycle DI classes.
 *
 * @author Vladimir Rybkin
 */
interface MainActivityLifeDI {

    companion object {

        const val COMPONENT_NAME = "MainActivityLifeDI"

    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityLifeScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class InitialRoute

    @Subcomponent(modules = arrayOf(MainActivityLifeModule::class, MainActivityRouteModule::class))
    @ActivityLifeScope
    interface MainActivityLifeComponent : MembersInjector<MainActivityLife> {

        fun createHomeScreenComponent(homeScreenModule: HomeScreenDI.HomeScreenModule): HomeScreenComponent

        fun createHomeTopScreenComponent(homeTopScreenModule: HomeTopScreenDI.HomeTopScreenModule): HomeTopScreenComponent

        fun createSplashScreenComponent(splashScreenModule: SplashScreenDI.SplashScreenModule,
                                        routingModule: SplashScreenRoutingModule): SplashScreenComponent

        fun createCollapseScreenComponent(collapseScreenModule: CollapseScreenDI.CollapseScreenModule): CollapseScreenComponent

    }

    @Module
    class MainActivityLifeModule(val drawerLayout: DrawerLayout,
                                 val navigationView: NavigationView) {

        @Provides
        fun provideSidemenuObserver(sidemenuOwner: SidemenuOwner): SidemenuObserver = sidemenuOwner

        @Provides
        fun provideSidemenuController(sidemenuOwner: SidemenuOwner): SidemenuController = sidemenuOwner

        @Provides @ActivityLifeScope
        fun provideSidemenuOwner(drawerPresenter: DrawerContract.Presenter,
                                 navigationPresenter: NavigationContract.Presenter):
                SidemenuOwner = SidemenuOwner(drawerPresenter, navigationPresenter)

        @Provides @ActivityLifeScope
        fun provideDrawerPresenter(activity: Activity): DrawerContract.Presenter {
            val drawerPresenter = DrawerPresenter()
            DrawerViewWrapper(activity, drawerLayout, drawerPresenter)
            return drawerPresenter
        }

        @Provides @ActivityLifeScope
        fun provideNavigationPresenter(
                @HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                @CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri
        ): NavigationContract.Presenter {
            val navigationPresenter = NavigationPresenter(R.menu.sidebar_menu,
                    mapOf(
                            Pair(R.id.sidebar_home, homeScreenKey),
                            Pair(R.id.sidebar_collapse_recycler, collapseScreenKey)
                    )
            )
            NavigationViewWrapper(navigationView, navigationPresenter)
            return navigationPresenter
        }

        @Provides @ActivityLifeScope
        fun provideSidemenuSubscriber(
                @HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                @HomeScreenDI.HomeScreenQualifier homeRoute: UriRoute,
                @CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri,
                @CollapseScreenDI.CollapseScreenQualifier collapseRoute: UriRoute
        ): Subscriber<Uri> = object : Subscriber<Uri>() {

            override fun onError(e: Throwable?) = Unit

            override fun onNext(key: Uri) = when (key) {
                homeScreenKey -> homeRoute.go(navigationView.context)
                collapseScreenKey -> collapseRoute.go(navigationView.context)
                else -> Unit
            }

            override fun onCompleted() = Unit
        }
    }

    @Module
    class MainActivityRouteModule(val context: Context,
                                  val screenContainer: ViewGroup) {

        @SimpleViewRouterQualifier
        @Provides @ActivityLifeScope
        fun provideBackRouteUriProvider(simpleViewUriRouter: SimpleViewUriRouter): BackRouteUriProvider = simpleViewUriRouter

        @SimpleViewRouterQualifier
        @Provides @ActivityLifeScope
        fun provideSimpleViewRouteAsUriRouter(simpleViewUriRouter: SimpleViewUriRouter): UriRouter = simpleViewUriRouter

        @Provides @ActivityLifeScope
        fun provideSimpleViewRoute(bootstrapProvider: BootstrapProvider,
                                   toolbarController: ToolbarController,
                                   sidemenuOwner: SidemenuOwner,
                                   @SplashScreenDI.SplashScreenQualifier splashScreenKey: Uri,
                                   @SplashScreenDI.SplashScreenQualifier splashScreenProducer: UriLifeProducer,
                                   @HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                                   @HomeScreenDI.HomeScreenQualifier homeScreenProducer: UriLifeProducer,
                                   @HomeTopScreenDI.HomeTopScreenQualifier homeTopScreenKey: Uri,
                                   @HomeTopScreenDI.HomeTopScreenQualifier homeTopScreenProducer: UriLifeProducer,
                                   @CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri,
                                   @CollapseScreenDI.CollapseScreenQualifier collapseProducer: UriLifeProducer): SimpleViewUriRouter =
                SimpleViewUriRouter("mainrouter", screenContainer)
                        .also {
                            it.replaceDefaultExecutor(object : DefaultRouterExecutor() {
                                override fun createPreTransition(
                                        context: Context,
                                        keyIn: Uri,
                                        keyOut: Uri?,
                                        inLife: Life, inData: Bundle?, savedState: Bundle?,
                                        outLife: Life?): Completable {

                                    return Completable
                                            .create(LifePreconditions(
                                                    context,
                                                    (inLife as Any).javaClass,
                                                    bootstrapProvider,
                                                    provideSplashScreenRoute(splashScreenKey, it).apply {
                                                        data(SplashScreenDI.State(inData, savedState).toBundle())
                                                    }.apply {
                                                        if (keyOut != null)
                                                            backRoute(RouterAction.ACTION_REPLACE_TOP.createRouterUri("", it.authority, keyOut))
                                                    }
                                            ))
                                            .andThen(Completable.create(MainActivityRouterPreConditions(keyIn,
                                                    sidemenuOwner, toolbarController)))
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

                                override fun createPostTransition(context: Context,
                                                                  containerView: ViewGroup,
                                                                  keyIn: Uri,
                                                                  keyOut: Uri?,
                                                                  inLife: Life, inData: Bundle?, savedState: Bundle?,
                                                                  outLife: Life?): Completable {
                                    return Completable.create(
                                            MainActivityRouterPostConditions(sidemenuOwner, toolbarController,
                                                    inLife, containerView))
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
                            addProducer(homeTopScreenKey, homeTopScreenProducer)
                        }
                        .apply {
                            addProducer(collapseScreenKey, collapseProducer)
                        }

        @Provides @MainActivityLifeDI.InitialRoute
        fun provideInitialRoute(@HomeScreenDI.HomeScreenQualifier homeScreenRoute: UriRoute): UriRoute
                = homeScreenRoute

        @Provides @ActivityLifeScope
        @HomeScreenDI.HomeScreenQualifier
        fun provideHomeScreenLifeProducer(): UriLifeProducer {
            val activityLifeComponent = context.getSystemService(COMPONENT_NAME)
                    as MainActivityLifeDI.MainActivityLifeComponent
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return HomeScreen({ module ->
                        activityLifeComponent.createHomeScreenComponent(module)
                    })
                }
            }
        }

        @Provides
        @HomeScreenDI.HomeScreenQualifier
        fun provideHomeScreenRoute(@HomeScreenDI.HomeScreenQualifier homeScreenKey: Uri,
                                   @SimpleViewRouterQualifier router: UriRouter): UriRoute =
                router.createRoute(homeScreenKey, RouterAction.ACTION_REPLACE_TOP)

        @Provides @ActivityLifeScope
        @HomeTopScreenDI.HomeTopScreenQualifier
        fun provideHomeTopScreenLifeProducer(): UriLifeProducer {
            val activityLifeComponent = context.getSystemService(COMPONENT_NAME)
                    as MainActivityLifeDI.MainActivityLifeComponent
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return HomeTopScreen({ module ->
                        activityLifeComponent.createHomeTopScreenComponent(module)
                    })
                }
            }
        }

        @Provides
        @HomeTopScreenDI.HomeTopScreenQualifier
        fun provideHomeTopScreenRoute(@HomeTopScreenDI.HomeTopScreenQualifier homeTopScreenKey: Uri,
                                      @SimpleViewRouterQualifier router: UriRouter): UriRoute =
                router.createRoute(homeTopScreenKey, RouterAction.ACTION_REPLACE_TOP)

        @Provides @ActivityLifeScope
        @CollapseScreenDI.CollapseScreenQualifier
        fun provideCollapseScreenLifeProducer(): UriLifeProducer {
            val activityLifeComponent = context.getSystemService(COMPONENT_NAME)
                    as MainActivityLifeDI.MainActivityLifeComponent
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return CollapseScreen({
                        activityLifeComponent.createCollapseScreenComponent(CollapseScreenDI.CollapseScreenModule())
                    })
                }
            }
        }

        @Provides
        @CollapseScreenDI.CollapseScreenQualifier
        fun provideCollapseScreenRoute(@CollapseScreenDI.CollapseScreenQualifier collapseScreenKey: Uri,
                                       @SimpleViewRouterQualifier router: UriRouter): UriRoute =
                router.createRoute(collapseScreenKey, RouterAction.ACTION_REPLACE_TOP)

        @Provides @ActivityLifeScope
        @SplashScreenDI.SplashScreenQualifier
        fun provideSplashScreenLifeProducer(): UriLifeProducer {
            val activityLifeComponent = context.getSystemService(COMPONENT_NAME)
                    as MainActivityLifeDI.MainActivityLifeComponent
            return object : UriLifeProducer {
                override fun produceLife(key: Uri, data: Bundle?): Life? {
                    return SplashScreen({ screenModule ->
                        activityLifeComponent.createSplashScreenComponent(screenModule,
                                SplashScreenRoutingModule())
                    })
                }
            }
        }

        @Provides
        @SplashScreenDI.SplashScreenQualifier
        fun provideSplashScreenRoute(@SplashScreenDI.SplashScreenQualifier splashScreenKey: Uri,
                                     @SimpleViewRouterQualifier router: UriRouter): UriRoute =
                router.createRoute(splashScreenKey, RouterAction.ACTION_REPLACE_TOP)
    }

}
