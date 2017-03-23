package com.vladimirrybkin.cycling2.lib_core.domain.route.uri

import android.net.Uri
import android.os.Bundle
import com.vladimirrybkin.cycling2.lib_core.domain.route.base.Router
import com.vladimirrybkin.cycling2.lib_core.presentation.life.uri.UriLifeProducer

/**
 * A URI based router class.
 *
 * @author Vladimir Rybkin
 */
abstract class UriRouter: Router<Uri> {

    private var producers: MutableMap<Uri, UriLifeProducer> = mutableMapOf()

    fun addProducer(key: Uri, producer: UriLifeProducer) = producers.put(key, producer)

    fun <T:UriLifeProducer> addProducers(producers: Map<Uri, T>) = this.producers.putAll(producers)

    open protected fun produceLife(key: Uri, data: Bundle?) = producers[key]?.produceLife(key, data)

    override var destroyed: Boolean = false

}
