package com.vladimirrybkin.cycling2.lib_core_impl.presentation.life.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.vladimirrybkin.cycling2.lib_core.presentation.activity.ActivityLifeDispatcher
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.cycling2.lib_core_impl.R
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

/**
 * An activity with a life cycle support.
 *
 * @author Vladimir Rybkin
 */
abstract class LifeActivity : AppCompatActivity() {

    protected val lifeCycleDispatcher = createLifeCycleDispatcher()

    override fun attachBaseContext(context : Context) {
        super.attachBaseContext(lifeCycleDispatcher.onActivityAttachBaseContext(context))
    }

    protected abstract fun createLifeCycle() : Life

    open protected fun setContentView() {
        frameLayout {
            id = R.id.life_container
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = false
        }
    }

    protected fun getContainerViewId() : Int = R.id.life_container

    protected fun createLifeCycleDispatcher(): ActivityLifeDispatcher =
            DefaultActivityLifeDispatcher(createLifeCycle())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isFinishing) {
            setContentView()
            val containerView: ViewGroup = findViewById(getContainerViewId()) as ViewGroup
            lifeCycleDispatcher.onActivityCreate(containerView, savedInstanceState, intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityStart()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityResume()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityPostResume()
        }
    }

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        lifeCycleDispatcher.onActivitySaveInstance(outState)
    }

    override fun onPause() {
        super.onPause()
        lifeCycleDispatcher.onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        lifeCycleDispatcher.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleDispatcher.onActivityDestroy()
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        lifeCycleDispatcher.onActivityResult(requestCode, resultCode, data)
    }

    override fun onConfigurationChanged(config : Configuration) {
        super.onConfigurationChanged(config)
        lifeCycleDispatcher.onActivityConfigurationChanged(config)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return lifeCycleDispatcher.onActivityCreateOptionsMenu(menu) || super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return lifeCycleDispatcher.onActivityPrepareOptionsMenu(menu) || super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return lifeCycleDispatcher.onActivityOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!lifeCycleDispatcher.onActivityBackPressed()) {
            super.onBackPressed()
        }
    }

}
