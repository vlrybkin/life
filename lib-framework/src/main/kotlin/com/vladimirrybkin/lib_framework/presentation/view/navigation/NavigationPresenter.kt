package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.support.annotation.MenuRes
import android.view.MenuItem
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.Subscriptions

/**
 * A navigation view presenter.
 *
 * @author Vladimir Rybkin
 */
class NavigationPresenter(@MenuRes val menuResId: Int = -1) : NavigationContract.Presenter {

    private val itemSelectedSubject: PublishSubject<MenuItem> = PublishSubject.create<MenuItem>()
    private var itemSelectedSubsciption: Subscription = Subscriptions.unsubscribed()
    private var view: NavigationContract.View? = null

    override fun attachView(view: NavigationContract.View) {
        this.view = view
        itemSelectedSubsciption = view.observeMenuItem().subscribe(itemSelectedSubject)
        if (menuResId > 0 && !view.inflated()) view.inflateMenu(menuResId)
    }

    override fun detachView() {
        itemSelectedSubsciption.unsubscribe()
        this.view = null
    }

    override fun observeMenuItem(): Observable<MenuItem> = itemSelectedSubject

    override fun inflateMenu(@MenuRes resId: Int) = view?.inflateMenu(resId)

    override fun selectItem(id: Int) = view?.selectItem(id)

}
