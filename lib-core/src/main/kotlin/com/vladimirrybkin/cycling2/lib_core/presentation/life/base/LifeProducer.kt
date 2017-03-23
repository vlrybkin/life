package com.vladimirrybkin.cycling2.lib_core.presentation.life.base

import android.os.Bundle

/**
 * A life producer interface.
 *
 * @author Vladimir Rybkin
 */
interface LifeProducer<in K> {

    fun produceLife(key: K, data: Bundle?): Life?

}
