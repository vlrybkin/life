package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.os.Bundle
import com.vladimirrybkin.cycling2.activities.presentation.app.MainApplicationDI
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.domain.di.life.DIActivity
import com.vladimirrybkin.lib_framework.presentation.life.ParentLife
import io.michaelrocks.lightsaber.Injector
import io.michaelrocks.lightsaber.Lightsaber

class MainActivity : DIActivity() {

    private lateinit var activityInjector: Injector

    override fun onCreate(savedInstanceState: Bundle?) {
        val appInjector = contextWrapper!!.getSystemService(MainApplicationDI.COMPONENT_NAME) as Injector
        activityInjector = Lightsaber.get().createChildInjector(appInjector,
                MainActivityDI.MainActivityComponent(this))
        addComponent(MainActivityDI.COMPONENT_NAME, activityInjector)
        super.onCreate(savedInstanceState)
    }

    override fun createLifeCycle(): Life = ParentLife(MainActivityLife(this::injectLife))

    private fun injectLife(module: MainActivityLifeDI.MainActivityLifeModule,
                           routeModule: MainActivityLifeDI.MainActivityRouteModule): Injector {
        val injector = Lightsaber.get().createChildInjector(activityInjector,
                MainActivityLifeDI.MainActivityLifeComponent(module, routeModule))
        return injector
    }

}
