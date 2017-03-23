package com.vladimirrybkin.lib_framework.domain.di

/**
 * DI storage interface.
 *
 * @author Vladimir Rybkin
 */
interface DIStorage {

    fun addComponent(name : String, component : Any)

    fun findComponent(name : String) : Any?

}
