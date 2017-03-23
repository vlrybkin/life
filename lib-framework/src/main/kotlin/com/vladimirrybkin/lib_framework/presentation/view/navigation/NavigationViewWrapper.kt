package com.vladimirrybkin.lib_framework.presentation.view.navigation

import android.support.design.widget.NavigationView
import android.view.MenuItem
import android.view.View
import rx.Observable
import rx.android.MainThreadSubscription
import rx.android.MainThreadSubscription.verifyMainThread
import java.util.*

/**
 * A navigation view wrapper implementing the contract interface.
 *
 * @author Vladimir Rybkin
 */
class NavigationViewWrapper(val navigationView: NavigationView,
                            val presenter: NavigationContract.Presenter)
    : NavigationContract.View {
    private var watchers = LinkedList<ItemSelectedWatcher>()

    private interface ItemSelectedWatcher {

        fun onItemSelected(item: MenuItem)

    }

    private val itemSelectedObservable: Observable<MenuItem> = Observable.create {
        verifyMainThread()

        val watcher = object: ItemSelectedWatcher {

            override fun onItemSelected(item: MenuItem) = it.onNext(item)

        }

        it.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                watchers.remove(watcher)
            }
        })

        watchers.add(watcher)
    }

    init {
        navigationView.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) = presenter.attachView(this@NavigationViewWrapper)

            override fun onViewDetachedFromWindow(v: View) = presenter.detachView()

        })

        navigationView.setNavigationItemSelectedListener { notifyItemSelected(it) }
        if (navigationView.isAttachedToWindow) presenter.attachView(this@NavigationViewWrapper)
    }

    private fun notifyItemSelected(item: MenuItem): Boolean {
        watchers.forEach { it.onItemSelected(item) }
        return true
    }

    override fun observeMenuItem(): Observable<MenuItem> = itemSelectedObservable.distinctUntilChanged()

    override fun inflateMenu(resId: Int) = navigationView.inflateMenu(resId)

    override fun selectItem(id: Int) = navigationView.setCheckedItem(id)

    override fun inflated(): Boolean = navigationView.menu.size() > 0

}
