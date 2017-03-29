package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.net.Uri
import rx.Observable

/**
 * A navigation view item click observable.
 *
 * @author Vladimir Rybkin
 */
interface NavigationItemSelectedObservable {

    fun observeMenuItem(): Observable<Uri>

}
