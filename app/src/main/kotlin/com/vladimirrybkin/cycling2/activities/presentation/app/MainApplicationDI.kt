package com.vladimirrybkin.cycling2.activities.presentation.app

import android.app.Application
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKeysModule
import io.michaelrocks.lightsaber.Component
import io.michaelrocks.lightsaber.Module
import io.michaelrocks.lightsaber.Provides

/**
 * Application DI classes.
 *
 * @author Vladimir Rybkin
 */
class MainApplicationDI {

    companion object {

        const val COMPONENT_NAME = "MainApplicationDI"

    }

    @Component
    class MainApplicationComponent(val application: MainApplication) {

        @Provides
        fun provideMainApplicationModule(): MainApplicationModule = MainApplicationModule(application)

        @Provides
        fun provideLifeKeysModule(): LifeKeysModule = LifeKeysModule()

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
