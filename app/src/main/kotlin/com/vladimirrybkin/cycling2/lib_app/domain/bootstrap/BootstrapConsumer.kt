package com.vladimirrybkin.cycling2.lib_app.domain.bootstrap

import com.vladimirrybkin.cycling2.lib_app.data.model.Bootstrap

/**
 * A bootstrap consumer interface.
 *
 * @author Vladimir Rybkin
 */
interface BootstrapConsumer {

    fun consumeBootstrap(bootstrap: Bootstrap)

    fun clearBootstrap()

}
