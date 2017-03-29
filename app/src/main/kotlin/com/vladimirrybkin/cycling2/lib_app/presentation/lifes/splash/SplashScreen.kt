package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash

import android.os.Bundle
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_app.data.model.DummyBootstrap
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKey
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.domain.route.RouteBack
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import dagger.MembersInjector
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * The splash screen life.
 *
 * @author Vladimir Rybkin
 */
@LifeKey(value = "/screen/splash")
@ParentLayout(layoutId = R.layout.screen_splash)
@BootstrapRequired(false)
class SplashScreen(val injector: (SplashScreenDI.SplashScreenModule) -> MembersInjector<SplashScreen>) : Life {

    @Inject
    lateinit var bootstrapConsumer: BootstrapConsumer

    @field:[Inject RouteBack]
    lateinit var nextScreenRoute: UriRoute

    override fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {
        super.onCreateView(parentViewGroup, inState)
        if (inState == null) throw RuntimeException("Splash screen state is not configured")
        injector(SplashScreenDI.SplashScreenModule(inState)).injectMembers(this)
    }

    override fun onStart() {
        super.onStart()
        Observable.just(Any()).delay(5, TimeUnit.SECONDS)
                .subscribe({
                    bootstrapConsumer.consumeBootstrap(DummyBootstrap())
                    nextScreenRoute.go()
                })
    }

}
