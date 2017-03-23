package com.vladimirrybkin.lib_framework.presentation.view.drawer

import android.content.res.Configuration

/**
 * A side drawer controller interface.
 *
 * @author Vladimir Rybkin
 */
interface DrawerController {

    fun open(): Unit?

    fun close(): Unit?

    fun lock(): Unit?

    fun unlock(): Unit?

    fun onConfigurationChanged(config: Configuration): Unit?

    fun syncState(): Unit?

}
