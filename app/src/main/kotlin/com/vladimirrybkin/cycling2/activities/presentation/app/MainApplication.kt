package com.vladimirrybkin.cycling2.activities.presentation.app

import android.app.Application
import android.content.Context
import com.vladimirrybkin.cycling2.lib_app.data.model.Bootstrap
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapConsumer
import com.vladimirrybkin.cycling2.lib_app.domain.bootstrap.BootstrapProvider
import com.vladimirrybkin.cycling2.lib_app.presentation.lifes.LifeKeysModule
import com.vladimirrybkin.lib_framework.domain.di.DIContextWrapper

/**
 * The main application instance.
 *
 * @author Vladimir Rybkin
 */
class MainApplication : Application(), BootstrapProvider, BootstrapConsumer {

    private lateinit var contextWrapper: DIContextWrapper

    private var bootstrap: Bootstrap? = null

    override fun attachBaseContext(base: Context) {
        contextWrapper = DIContextWrapper(base, true)

        val appComponent = DaggerMainApplicationDI_MainApplicationComponent.builder()
                        .mainApplicationModule(MainApplicationDI.MainApplicationModule(this))
                        .lifeKeysModule(LifeKeysModule())
                        .build()
        contextWrapper.addComponent(MainApplicationDI.COMPONENT_NAME, appComponent)
        super.attachBaseContext(contextWrapper)
    }

    override fun getBootstrap(): Bootstrap? = bootstrap

    override fun consumeBootstrap(bootstrap: Bootstrap) {
        this.bootstrap = bootstrap
    }

}
