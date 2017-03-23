package com.vladimirrybkin.lib_framework.domain.di.life

import android.content.Context
import com.vladimirrybkin.cycling2.lib_core_impl.presentation.life.activity.LifeActivity
import com.vladimirrybkin.lib_framework.domain.di.DIContextWrapper
import com.vladimirrybkin.lib_framework.domain.di.DIStorage

/**
 * An activity with a context wrapper for DI.
 *
 * @author Vladimir Rybkin
 */
abstract class DIActivity : LifeActivity(), DIStorage {

    protected var contextWrapper: DIContextWrapper? = null

    override fun attachBaseContext(context: Context) {
        val localContextWrapper = DIContextWrapper(context)
        this.contextWrapper = localContextWrapper
        super.attachBaseContext(localContextWrapper)
    }

    override fun addComponent(name: String, component: Any) {
        contextWrapper!!.addComponent(name, component)
    }

    override fun findComponent(name: String): Any? {
        return contextWrapper!!.findComponent(name)
    }

}
