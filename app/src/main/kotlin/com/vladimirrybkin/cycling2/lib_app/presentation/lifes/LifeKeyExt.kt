package com.vladimirrybkin.cycling2.lib_app.presentation.lifes

/**
 * Get a life key.
 *
 * @author Vladimir Rybkin
 */
fun <T> getKey(clazz: Class<T>): String {
    val annotation = clazz.getAnnotation(LifeKey::class.java)
    return annotation?.value ?: clazz.simpleName
}
