package com.vladimirrybkin.cycling2.lib_app.domain.preconditions

import android.content.Context
import com.vladimirrybkin.cycling2.lib_app.data.annotations.BootstrapRequired
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_core.domain.route.uri.UriRoute
import rx.Completable

/**
 * A lifecycle preconditions check.
 *
 * @author Vladimir Rybkin
 */
class LifePreconditions<T>(
        val context: Context,
        val clazz: Class<T>,
        val bootstrapProvider: BootstrapProvider,
        val failedRoute: UriRoute) : Completable.CompletableOnSubscribe {

    override fun call(subsciber: Completable.CompletableSubscriber) {
        val bootstrapAnnotation = clazz.getAnnotation(BootstrapRequired::class.java)
        if ((bootstrapAnnotation == null || bootstrapAnnotation.required) &&
                bootstrapProvider.getBootstrap() == null) {
            failedRoute.go(context)
            subsciber.onError(RuntimeException()) //TODO: BAD PRACTICE! NEED ANOTHER WAY TO TERMINATE THE SEQUENCE
        } else {
            subsciber.onCompleted()
        }
    }

}
