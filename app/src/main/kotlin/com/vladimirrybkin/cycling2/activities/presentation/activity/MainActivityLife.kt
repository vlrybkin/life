package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRouter
import com.vladimirrybkin.lib_framework.R
import com.vladimirrybkin.lib_framework.domain.di.life.DILife
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu.SidemenuOwner
import dagger.MembersInjector
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions
import javax.inject.Inject

/**
 * The main activity life cycle.
 *
 * @author Vladimir Rybkin
 */
@ParentLayout(layoutId = R.layout.drawer)
class MainActivityLife(val inject: (MainActivityLifeDI.MainActivityLifeModule,
                                    MainActivityLifeDI.MainActivityRouteModule) ->
        MembersInjector<MainActivityLife>) : DILife() {

    companion object {
        const val EXTRA_ROUTER = "MainActivityLife_EXTRA_ROUTER"
    }

    @Inject
    lateinit var router: UriRouter

    @Inject
    lateinit var sidemenuOwner: SidemenuOwner

    @field:[Inject MainActivityLifeDI.InitialRoute]
    lateinit var initialRoute: UriRoute

    @Inject
    lateinit var sidemenuSubscriber: Subscriber<Uri>

    private var menuSubscription = Subscriptions.unsubscribed()

    override fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {
        super.onCreateView(parentViewGroup, inState)

        val navigationView = LayoutInflater.from(contextWrapper).inflate(
                R.layout.drawer_nav,
                parentViewGroup.findViewById(R.id.navigation_container) as ViewGroup,
                false) as NavigationView
        (parentViewGroup.findViewById(R.id.navigation_container) as ViewGroup).addView(navigationView)

        val injector = inject(
                MainActivityLifeDI.MainActivityLifeModule(parentViewGroup as DrawerLayout, navigationView),
                MainActivityLifeDI.MainActivityRouteModule(contextWrapper as Context,
                        parentViewGroup.findViewById(R.id.screen_container) as ViewGroup)
        )

        addComponent(MainActivityLifeDI.COMPONENT_NAME, injector)
        injector.injectMembers(this)

        menuSubscription = sidemenuOwner.observeMenuItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sidemenuSubscriber)
    }

    override fun onRestoreState(savedState: Bundle) {
        super.onRestoreState(savedState)
        router.restore(savedState.getBundle(EXTRA_ROUTER))
        sidemenuOwner.syncState()
    }

    override fun onStart() {
        super.onStart()
        if (!router.hasLife()) initialRoute.go()
    }

    override fun onSaveState(outState: Bundle) {
        super.onSaveState(outState)
        outState.putBundle(EXTRA_ROUTER, router.save(Bundle()))
    }

    override fun onBackPressed(): Boolean {
        if (sidemenuOwner.onBackPressed() || router.backPressed()) return true
        else return super.onBackPressed()
    }

    override fun onConfigurationChanged(config: Configuration) {
        super.onConfigurationChanged(config)
        sidemenuOwner.onConfigurationChanged(config)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuSubscription.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        router.destroy()
        menuSubscription.unsubscribe()
    }

}
