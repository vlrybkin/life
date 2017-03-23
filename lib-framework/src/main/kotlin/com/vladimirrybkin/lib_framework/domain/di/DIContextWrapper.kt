package com.vladimirrybkin.lib_framework.domain.di

import android.content.Context
import android.content.ContextWrapper
import java.util.*

/**
 * A context wrapper to provide access to components via a context.
 *
 * @author Vladimir Rybkin
 */
class DIContextWrapper(base : Context, val root : Boolean = false) : ContextWrapper(base),
        DIStorage {

    var components : HashMap<String, Any> = HashMap()

    override fun getSystemService(name : String) : Any? {
        return components[name] ?: super.getSystemService(name) ?: getFromApplicationContext(name)
    }

    fun getFromApplicationContext(name: String) : Any? =
        if (root) null else applicationContext.getSystemService(name)

    override fun addComponent(name : String, component : Any) {
        components.put(name, component)
    }

    override fun findComponent(name : String) : Any? = components[name]

}
