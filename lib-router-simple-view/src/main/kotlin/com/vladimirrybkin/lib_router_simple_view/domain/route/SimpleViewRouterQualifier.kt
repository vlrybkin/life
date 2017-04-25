package com.vladimirrybkin.lib_router_simple_view.domain.route

import javax.inject.Qualifier

/**
 * A simple view router qualifier.
 *
 * @author Vladimir Rybkin
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SimpleViewRouterQualifier(val value: String = "")
