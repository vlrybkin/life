package com.vladimirrybkin.lib_framework.presentation.view.drawer

import android.content.res.Configuration
import android.view.MenuItem
import rx.Observable
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subscriptions.Subscriptions

/**
 * A drawer presenter class.
 *
 * @author Vladimir Rybkin
 */
class DrawerPresenter : DrawerContract.Presenter {
    val openStateSubject: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()
    var openStateSubsciption: Subscription = Subscriptions.unsubscribed()

    var view: DrawerContract.View? = null

    override fun attachView(view: DrawerContract.View) {
        this.view = view
        openStateSubsciption = view.observeOpenState().subscribe(openStateSubject)
    }

    override fun detachView() {
        openStateSubsciption.unsubscribe()
        this.view = null
    }

    override fun observeOpenState(): Observable<Boolean> = openStateSubject.asObservable()

    override fun open() {
        view?.open()
    }

    override fun close() {
        view?.close()
    }

    override fun lock() {
        view?.lock()
    }

    override fun unlock() {
        view?.unlock()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = view?.onOptionsItemSelected(item) ?: false

    override fun onConfigurationChanged(config: Configuration) {
        view?.onConfigurationChanged(config)
    }

    override fun syncState() {
        view?.syncState()
    }

    override fun isOpen(): Boolean = view?.isOpen() ?: false

    override fun boundToToolbar(bound: Boolean) {
        view?.boundToToolbar(bound)
    }

}
