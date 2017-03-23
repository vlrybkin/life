package com.vladimirrybkin.lib_framework.presentation.view.drawer

import rx.Observable

/**
 * A drawer state observable.
 *
 * @author Vladimir Rybkin
 */
interface DrawerStateObservable {

    fun observeOpenState(): Observable<Boolean>

    fun isOpen(): Boolean

}
