package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.os.Bundle
import com.vladimirrybkin.cycling2.activities.presentation.app.MainApplicationDI
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.domain.di.life.DIActivity
import com.vladimirrybkin.lib_framework.presentation.life.ParentLife
import dagger.MembersInjector

class MainActivity : DIActivity() {

    private lateinit var activityComponent: MainActivityDI.MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = contextWrapper!!.getSystemService(MainApplicationDI.COMPONENT_NAME)
                as MainApplicationDI.MainApplicationComponent
        activityComponent = appComponent.createActivityComponent(
                MainActivityDI.MainActivityModule(this))
        addComponent(MainActivityDI.COMPONENT_NAME, activityComponent)
        super.onCreate(savedInstanceState)
    }

    override fun createLifeCycle(): Life = ParentLife(MainActivityLife(this::injectLife))

    private fun injectLife(module: MainActivityLifeDI.MainActivityLifeModule,
                           routeModule: MainActivityLifeDI.MainActivityRouteModule):
            MembersInjector<MainActivityLife> =

            activityComponent.createMainActivityLifeComponent(module, routeModule)

}
