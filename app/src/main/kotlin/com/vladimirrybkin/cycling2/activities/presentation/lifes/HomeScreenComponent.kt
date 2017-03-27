package com.vladimirrybkin.cycling2.activities.presentation.lifes

import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreen
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.home.HomeScreenDI
import dagger.MembersInjector
import dagger.Subcomponent

/**
 * The home screen component.
 *
 * @author Vladimir Rybkin
 */
@Subcomponent( modules = arrayOf(HomeScreenDI.HomeScreenModule::class))
interface HomeScreenComponent: MembersInjector<HomeScreen>
