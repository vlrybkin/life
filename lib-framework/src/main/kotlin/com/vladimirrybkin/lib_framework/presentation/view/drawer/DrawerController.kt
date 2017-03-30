package com.vladimirrybkin.lib_framework.presentation.view.drawer

import android.content.res.Configuration
import android.view.MenuItem

/**
 * A side drawer controller interface.
 *
 * @author Vladimir Rybkin
 */
interface DrawerController {

    fun open()

    fun close()

    fun lock()

    fun unlock()

    fun onOptionsItemSelected(item: MenuItem): Boolean

    fun onConfigurationChanged(config: Configuration)

    fun syncState()

    fun boundToToolbar(bound: Boolean)

}
