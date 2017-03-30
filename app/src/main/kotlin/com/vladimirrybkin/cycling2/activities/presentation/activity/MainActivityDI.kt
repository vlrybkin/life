package com.vladimirrybkin.cycling2.activities.presentation.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.vladimirrybkin.lib_framework.presentation.view.appbar.AppBarController
import com.vladimirrybkin.lib_framework.presentation.view.appbar.AppBarPresenter
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarController
import com.vladimirrybkin.lib_framework.presentation.view.compound.toolbar.ToolbarPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Activity DI classes.
 *
 * @author Vladimir Rybkin
 */
interface MainActivityDI {

    companion object {

        const val COMPONENT_NAME = "MainActivityDI"

    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityScope

    @ActivityScope
    @Subcomponent(modules = arrayOf(MainActivityModule::class))
    interface MainActivityComponent {

        fun createMainActivityLifeComponent(
                activityLifeComponent: MainActivityLifeDI.MainActivityLifeModule,
                routeModule: MainActivityLifeDI.MainActivityRouteModule
        ): MainActivityLifeDI.MainActivityLifeComponent

    }

    @Module
    class MainActivityModule(val activity: AppCompatActivity) {

        @Provides
        fun provideActivity(): Activity {
            return activity
        }

        @Provides
        fun provideAppCompatActivity(): AppCompatActivity {
            return activity
        }

        @Provides @ActivityScope
        fun provideAppBarController(activity: AppCompatActivity): AppBarController = AppBarPresenter(activity)

        @Provides @ActivityScope
        fun provideToolbarController(appBarController: AppBarController): ToolbarController
                = ToolbarPresenter(appBarController)

    }

}
