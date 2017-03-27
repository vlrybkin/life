package com.vladimirrybkin.cycling2.activities.presentation.app

import android.app.Application
import com.vladimirrybkin.cycling2.activities.presentation.activity.MainActivityDI
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKeysModule
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Application DI classes.
 *
 * @author Vladimir Rybkin
 */
interface MainApplicationDI {

    companion object {

        const val COMPONENT_NAME = "MainApplicationDI"

    }

    @Component(modules = arrayOf(MainApplicationDI.MainApplicationModule::class, LifeKeysModule::class))
    interface MainApplicationComponent {

        fun createActivityComponent(activityModule: MainActivityDI.MainActivityModule):
                MainActivityDI.MainActivityComponent

    }

    @Module
    class MainApplicationModule(val application: MainApplication) {

        @Provides
        fun provideApplication(): Application {
            return application
        }

        @Provides
        fun provideBootstrapProvider(): BootstrapProvider = application

        @Provides
        fun provideBootstrapConsumer(): BootstrapConsumer = application

    }

}
