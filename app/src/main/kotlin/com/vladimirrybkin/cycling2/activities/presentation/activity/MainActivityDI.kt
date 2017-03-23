package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.vladimirrybkin.cycling2.activities.presentation.app.MainApplicationDI
import io.michaelrocks.lightsaber.Component
import io.michaelrocks.lightsaber.Module
import io.michaelrocks.lightsaber.Provides

/**
 * Activity DI classes.
 *
 * @author Vladimir Rybkin
 */
class MainActivityDI {

    companion object {

        const val COMPONENT_NAME = "MainActivityDI"

    }

    @Component(parents = arrayOf(MainApplicationDI.MainApplicationComponent::class))
    class MainActivityComponent(val activity: AppCompatActivity) {

        @Provides
        fun provideMainActivityModule(): MainActivityModule {
            return MainActivityModule(activity)
        }

    }

    @Module
    class MainActivityModule(val activity: Activity) {

        @Provides
        fun provideActivity(): Activity {
            return activity
        }

    }

}
