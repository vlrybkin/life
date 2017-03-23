package com.vladimirrybkin.lib_framework.domain.di.life

import android.content.Context
import com.vladimirrybkin.cycling2.lib_core.presentation.life.base.Life
import com.vladimirrybkin.lib_framework.domain.di.DIContextWrapper
import com.vladimirrybkin.lib_framework.domain.di.DIStorage

/**
 * A life cycle with a context wrapper for DI.
 *
 * @author Vladimir Rybkin
 */
open class DILife : Life, DIStorage {

    protected var contextWrapper: DIContextWrapper? = null

    override fun attachBaseContext(context: Context): Context {
        val localContextWrapper = DIContextWrapper(context)
        this.contextWrapper = localContextWrapper
        super.attachBaseContext(localContextWrapper)
        return localContextWrapper
    }

    override fun detachBaseContext() {
        super.detachBaseContext()
        contextWrapper = null
    }

    override fun addComponent(name: String, component: Any) {
        contextWrapper!!.addComponent(name, component)
    }

    override fun findComponent(name: String): Any? {
        return contextWrapper!!.findComponent(name)
    }

}
