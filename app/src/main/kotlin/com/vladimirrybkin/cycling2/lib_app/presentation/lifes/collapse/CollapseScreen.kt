package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse

import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import io.michaelrocks.lightsaber.Injector

/**
 * The collapsing screen life.
 *
 * @author Vladimir Rybkin
 */
@ParentLayout(layoutId = R.layout.screen_collapse_recycler)
@BootstrapRequired(true)
class CollapseScreen(val injector: () -> Injector) : Life {
    // TODO
}
