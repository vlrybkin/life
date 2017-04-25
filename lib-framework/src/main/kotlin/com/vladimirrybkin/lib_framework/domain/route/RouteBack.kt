package com.vladimirrybkin.lib_framework.domain.route

import javax.inject.Qualifier

/**
 * A back route qualifier.
 *
 * @author Vladimir Rybkin
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RouteBack(val value: String = "")
