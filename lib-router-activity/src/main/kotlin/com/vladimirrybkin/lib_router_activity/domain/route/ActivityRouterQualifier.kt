package com.vladimirrybkin.lib_router_activity.domain.route

import javax.inject.Qualifier

/**
 * Activity router qualifier.
 *
 * @author Vladimir Rybkin
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityRouterQualifier(val value: String = "")
