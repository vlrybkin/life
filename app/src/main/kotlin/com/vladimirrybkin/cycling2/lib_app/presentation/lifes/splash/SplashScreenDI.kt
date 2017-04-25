package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.splash

import android.os.Bundle
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

/**
 * Splash screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface SplashScreenDI {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SplashScreenQualifier(val value: String = "")

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SplashScreenTargetState

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SplashScreenTargetSavedState

    class State {

        companion object {
            private const val EXTRA_TARGET_STATE = "EXTRA_TARGET_STATE"
            private const val EXTRA_TARGET_SAVED_STATE = "EXTRA_TARGET_SAVED_STATE"
        }

        private val bundle: Bundle

        constructor(targetScreenState: Bundle?, targetScreenSavedState: Bundle?) {
            bundle = Bundle()
            bundle.putBundle(EXTRA_TARGET_STATE, targetScreenState)
            bundle.putBundle(EXTRA_TARGET_SAVED_STATE, targetScreenSavedState)
        }

        constructor(bundle: Bundle) {
            this.bundle = Bundle(bundle)
        }

        fun toBundle(): Bundle = Bundle(bundle)

        fun getTargetState() = bundle.getBundle(EXTRA_TARGET_STATE)

        fun getTargetSavedState() = bundle.getBundle(EXTRA_TARGET_SAVED_STATE)

    }

    @Module
    class SplashScreenModule(state: Bundle) {

        private val state = State(state)

        @Provides @SplashScreenDI.SplashScreenTargetState
        fun provideTargetState(): Bundle? = state.getTargetState()

        @Provides @SplashScreenDI.SplashScreenTargetSavedState
        fun provideTargetSavedState(): Bundle? = state.getTargetSavedState()

    }

}
