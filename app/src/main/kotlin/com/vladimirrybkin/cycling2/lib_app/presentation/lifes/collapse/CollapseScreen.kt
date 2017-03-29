package com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse

import com.vladimirrybkin.cycling2.activities.R
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKey
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.presentation.life.ParentLayout
import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerEnabled
import dagger.MembersInjector

/**
 * The collapsing screen life.
 *
 * @author Vladimir Rybkin
 */
@LifeKey(value = "/screen/collapse")
@ParentLayout(layoutId = R.layout.screen_collapse_recycler)
@BootstrapRequired(true)
@DrawerEnabled
class CollapseScreen(val injector: () -> MembersInjector<CollapseScreen>) : Life {
    // TODO
}
