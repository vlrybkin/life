package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Activity DI classes.
 *
 * @author Vladimir Rybkin
 */
interface MainActivityDI {

    companion object {

        const val COMPONENT_NAME = "MainActivityDI"

    }

    @Subcomponent(modules = arrayOf(MainActivityModule::class))
    interface MainActivityComponent {

        fun createMainActivityLifeComponent(
                activityLifeComponent: MainActivityLifeDI.MainActivityLifeModule,
                routeModule: MainActivityLifeDI.MainActivityRouteModule
        ): MainActivityLifeDI.MainActivityLifeComponent

    }

    @Module
    class MainActivityModule(val activity: Activity) {

        @Provides
        fun provideActivity(): Activity {
            return activity
        }

    }

}
