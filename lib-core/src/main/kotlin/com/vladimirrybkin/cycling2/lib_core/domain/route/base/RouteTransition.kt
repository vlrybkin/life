package com.vladimirrybkin.cycling2.lib_core.domain.route.base

import android.os.Bundle

/**
 * A route transition data class.
 *
 * @author Vladimir Rybkin
 */
class RouteTransition {

    protected val bundle: Bundle

    constructor() {
        bundle = Bundle()
    }

    constructor(bundle: Bundle) {
        this.bundle = Bundle(bundle)
    }

    fun toBundle(): Bundle = Bundle(bundle)

}
