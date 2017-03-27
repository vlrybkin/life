package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.collapse.CollapseScreenDI
import dagger.MembersInjector
import dagger.Subcomponent

/**
 * The collapse screen component.
 *
 * @author Vladimir Rybkin
 */
@Subcomponent( modules = arrayOf(CollapseScreenDI.CollapseScreenModule::class))
interface CollapseScreenComponent: MembersInjector<CollapseScreen>
