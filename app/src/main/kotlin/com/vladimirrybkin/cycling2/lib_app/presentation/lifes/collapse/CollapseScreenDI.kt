package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse

import io.michaelrocks.lightsaber.Module
import javax.inject.Qualifier

/**
 * Collapse screen DI data.
 *
 * @author Vladimir Rybkin
 */
interface CollapseScreenDI {

    @Qualifier
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class CollapseScreenQualifier(val value: String = "")


    @Module
    class CollapseScreenModule {
        // TODO
    }

}
