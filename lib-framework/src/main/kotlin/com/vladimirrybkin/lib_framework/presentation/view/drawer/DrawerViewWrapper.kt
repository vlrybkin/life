package com.vladimirrybkin.lib_framework.presentation.view.drawer

import android.app.Activity
import android.content.res.Configuration
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import com.vladimirrybkin.lib_framework.R
import rx.Observable
import rx.android.MainThreadSubscription
import rx.android.MainThreadSubscription.verifyMainThread
import java.util.*

/**
 * A drawer layout view wrapper.
 *
 * @author Vladimir Rybkin
 */
class DrawerViewWrapper(val activity: Activity,
                        val drawerLayout: DrawerLayout,
                        val presenter: DrawerContract.Presenter,
                        val openRes: Int = R.string.drawer_open,
                        val closeRes: Int = R.string.drawer_close)
    : DrawerContract.View {

    private val drawerToggle: ActionBarDrawerToggle
    private val watchers = LinkedList<OpenStateWatcher>()

    private var boundToToolbar: Boolean = false

    private interface OpenStateWatcher {

        fun onOpenStateChange(open: Boolean)

    }

    private val openStateObservable: Observable<Boolean> = Observable.create {
        verifyMainThread()

        val watcher = object: OpenStateWatcher {

            override fun onOpenStateChange(open: Boolean) {
                it.onNext(open)
            }

        }

        it.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                watchers.remove(watcher)
            }
        })

        watchers.add(watcher)

        // Emit initial value.
        it.onNext(drawerLayout.isDrawerOpen(Gravity.START))
    }

    init {
        drawerLayout.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) = presenter.attachView(this@DrawerViewWrapper)

            override fun onViewDetachedFromWindow(v: View) = presenter.detachView()

        })

        drawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout,
                openRes, closeRes) {

            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                watchers.forEach { it.onOpenStateChange(false) }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                watchers.forEach { it.onOpenStateChange(true) }
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun observeOpenState(): Observable<Boolean> =
            openStateObservable.distinctUntilChanged()

    override fun open() = drawerLayout.openDrawer(GravityCompat.START)

    override fun close() = drawerLayout.closeDrawer(GravityCompat.START)

    override fun lock() =  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    override fun unlock() =  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            if (boundToToolbar) drawerToggle.onOptionsItemSelected(item) else false

    override fun onConfigurationChanged(config: Configuration) =
            drawerToggle.onConfigurationChanged(config)

    override fun syncState() = if (boundToToolbar) drawerToggle.syncState() else {}

    override fun isOpen(): Boolean = drawerLayout.isDrawerOpen(Gravity.START)

    override fun boundToToolbar(bound: Boolean) {
        this.boundToToolbar = bound
        if (boundToToolbar) syncState()
    }

}
