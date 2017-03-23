package com.vladimirrybkin.cycling2.lib_core.presentation.life.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup

/**
 * A life cycle interface.
 *
 * @author Vladimir Rybkin
 */
interface Life {

    fun attachBaseContext(context: Context): Context = context

    fun onCreate(inState: Bundle?) {}

    fun onCreateView(parentViewGroup: ViewGroup, inState: Bundle?) {}

    fun onRestoreState(savedState: Bundle) {}

    fun onStart() {}

    fun onSaveState(outState: Bundle) {}

    fun onStop() {}

    fun onDestroyView() {}

    fun onDestroy() {}

    fun detachBaseContext() {}

    fun onBackPressed(): Boolean = false

    fun onResult(data: Bundle) {}

    fun onConfigurationChanged(config: Configuration) {}

    fun onOptionsItemSelected(item: MenuItem): Boolean = false

}
