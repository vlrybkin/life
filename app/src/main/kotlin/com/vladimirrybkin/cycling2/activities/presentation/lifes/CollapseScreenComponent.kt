package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import io.michaelrocks.lightsaber.Component
import io.michaelrocks.lightsaber.Provides

/**
 * The collapse screen component.
 *
 * @author Vladimir Rybkin
 */
@Component
class CollapseScreenComponent {

    @Provides
    fun collapseScreenModule(): CollapseScreenDI.CollapseScreenModule =
            CollapseScreenDI.CollapseScreenModule()

}
