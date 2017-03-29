package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.net.Uri
import android.support.annotation.MenuRes
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.Subscriptions

/**
 * A navigation view presenter.
 *
 * @author Vladimir Rybkin
 */
class NavigationPresenter(@MenuRes val menuResId: Int = -1,
                          val map: Map<Int, Uri>) : NavigationContract.Presenter {

    private val itemSelectedSubject: PublishSubject<Uri> = PublishSubject.create<Uri>()
    private var itemSelectedSubsciption: Subscription = Subscriptions.unsubscribed()
    private var view: NavigationContract.View? = null
    private var selectedItemId = -1

    override fun attachView(view: NavigationContract.View) {
        this.view = view
        itemSelectedSubsciption = view.observeMenuItem()
                .map { map[it.itemId] }
                .subscribe(itemSelectedSubject)
        if (menuResId > 0 && !view.inflated()) view.inflateMenu(menuResId)
        if (selectedItemId > 0) view.selectItem(selectedItemId)
    }

    override fun detachView() {
        itemSelectedSubsciption.unsubscribe()
        this.view = null
    }

    override fun observeMenuItem(): Observable<Uri> = itemSelectedSubject

    override fun selectItem(key: Uri) = map.values.indexOf(key)
            .let {
                if (it >= 0) {
                    selectedItemId = map.keys.elementAt(it)
                    view?.selectItem(selectedItemId)
                }
            }

}
