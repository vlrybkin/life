package com.vladimirrybkin.cycling2.lib_core.presentation.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life

/**
 * Activity dispatcher interface.
 *
 * @author Vladimir Rybkin
 */
interface ActivityLifeDispatcher {

    val life: Life

    fun intentToInitialState(intent : Intent?) : Bundle? = intent?.extras

    fun intentToResult(requestCode: Int, resultCode: Int, data: Intent) : Bundle = data.extras

    fun onActivityAttachBaseContext(context: Context) : Context = life.attachBaseContext(context)

    fun onActivityCreate(viewContainer: ViewGroup, savedState: Bundle?, intent : Intent?) {
        val initialState = intentToInitialState(intent)
        life.onCreate(initialState)
        life.onCreateView(viewContainer, initialState)

        if (savedState != null) life.onRestoreState(savedState)
    }

    fun onActivityStart() = life.onStart()

    fun onActivitySaveInstance(outState: Bundle) = life.onSaveState(outState)

    fun onActivityPause() {}

    fun onActivityResume() {}

    fun onActivityPostResume() {}

    fun onActivityStop() = life.onStop()

    fun onActivityDestroy() {
        life.onDestroyView()
        life.onDestroy()
        life.detachBaseContext()
    }

    fun onActivityBackPressed() : Boolean = life.onBackPressed()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) =
            life.onResult(requestCode, resultCode, intentToResult(requestCode, resultCode, data))

    fun onActivityConfigurationChanged(config: Configuration) = life.onConfigurationChanged(config)

    fun onActivityCreateOptionsMenu(menu: Menu): Boolean = life.onCreateOptionsMenu(menu)

    fun onActivityPrepareOptionsMenu(menu: Menu): Boolean = life.onPrepareOptionsMenu(menu)

    fun onActivityOptionsItemSelected(item: MenuItem): Boolean = life.onOptionsItemSelected(item)

}