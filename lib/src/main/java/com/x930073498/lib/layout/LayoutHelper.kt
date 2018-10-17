package com.x930073498.lib.layout

import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View

/**
 * Created by x930073498 on 2018/10/11.
 */
class LayoutHelper private constructor(private val adapter: LayoutAdapter) {

    private var view: Any? = null

    fun getView(): View? {
        return when (view) {
            is View -> view as View
            is Fragment -> getView(view as Fragment)
            is FragmentActivity -> getView(view as FragmentActivity)
            else -> null
        }
    }


    fun push(item: LayoutItem) {
        adapter.push(item)
    }


    fun push(id: Int, data: Any?) {
        adapter.push(id, data)
    }

    fun pushIndex(index: Int, data: Any?) {
        adapter.pushIndex(index, data)
    }

    fun <T> pushData(data: T, clazz: Class<T>) {
        adapter.pushData(data, clazz)
    }

    inline fun <reified T> pushData(data: T) {
        pushData(data, T::class.java)
    }

    fun push() {
        adapter.push()
    }

    fun getAdapter(): LayoutAdapter {
        return adapter
    }

    private fun getView(fragment: Fragment): View? {
        if (this.view is View) return view as View
        return (fragment.view)?.apply { this@LayoutHelper.view = this }
    }

    private fun getView(activity: FragmentActivity): View? {
        if (this.view is View) return view as View
        return (activity.window.decorView.findViewById<View>(android.R.id.content))?.apply {
            this@LayoutHelper.view = this
        }
    }


    companion object {
        fun attach(view: View, adapter: LayoutAdapter, owner: LifecycleOwner? = null): LayoutHelper {
            return attach(adapter, view).apply {
                if (owner != null) getAdapter().setLifecycle(owner)
            }
        }

        fun attach(fragment: Fragment, adapter: LayoutAdapter? = null): LayoutHelper {
            return attach(adapter, fragment).apply { getAdapter().setLifecycle(fragment) }
        }

        fun attach(activity: FragmentActivity, adapter: LayoutAdapter? = null): LayoutHelper {
            return attach(adapter, activity).apply {
                getAdapter().setLifecycle(activity)
            }
        }

        private fun attach(adapter: LayoutAdapter? = null, view: Any): LayoutHelper {

            return LayoutHelper(adapter ?: LayoutAdapter()).apply {
                this.view = view
                getAdapter().setHelper(this)
            }
        }
    }

}