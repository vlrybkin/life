package com.vladimirrybkin.cycling2.lib_app.domain.bootstrap

import com.vladimirrybkin.cycling2.lib_app.data.model.Bootstrap

/**
 * A bootstrap provider interface.
 *
 * @author Vladimir Rybkin
 */
interface BootstrapProvider {

    fun getBootstrap(): Bootstrap?

}
