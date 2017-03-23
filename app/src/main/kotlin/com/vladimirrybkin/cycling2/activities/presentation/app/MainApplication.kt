package com.vladimirrybkin.cycling2.activities.presentation.app

import android.app.Application
import android.content.Context
import com.vladimirrybkin.cycling2.lib_app.data.model.Bootstrap
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.lib_framework.domain.di.DIContextWrapper
import io.michaelrocks.lightsaber.Lightsaber

/**
 * The main application instance.
 *
 * @author Vladimir Rybkin
 */
class MainApplication : Application(), BootstrapProvider, BootstrapConsumer {

    private lateinit var contextWrapper: DIContextWrapper

    private var bootstrap: Bootstrap? = null

    override fun attachBaseContext(base : Context) {
        contextWrapper = DIContextWrapper(base, true)
        val appInjector = Lightsaber.get().createInjector(MainApplicationDI.MainApplicationComponent(this))
        contextWrapper.addComponent(MainApplicationDI.COMPONENT_NAME, appInjector)
        super.attachBaseContext(contextWrapper)
    }

    override fun getBootstrap(): Bootstrap? = bootstrap

    override fun consumeBootstrap(bootstrap: Bootstrap) {
        this.bootstrap = bootstrap
    }

}
