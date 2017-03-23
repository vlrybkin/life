package com.vladimirrybkin.lib_framework.presentation.view.compound.sidemenu

import com.vladimirrybkin.lib_framework.presentation.view.drawer.DrawerStateObservable
import com.vladimirrybkin.lib_framework.presentation.view.navigation.NavigationItemSelectedObservable

/**
 * A sidemenu observer interface.
 *
 * @author Vladimir Rybkin
 */
interface SidemenuObserver: NavigationItemSelectedObservable, DrawerStateObservable
