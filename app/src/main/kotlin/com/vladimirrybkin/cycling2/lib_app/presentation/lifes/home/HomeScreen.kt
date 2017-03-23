package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home

import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import io.michaelrocks.lightsaber.Injector

/**
 * The home screen life.
 *
 * @author Vladimir Rybkin
 */
@ParentLayout(layoutId = R.layout.screen_home)
@BootstrapRequired(true)
class HomeScreen(val injector: () -> Injector) : Life {

}
