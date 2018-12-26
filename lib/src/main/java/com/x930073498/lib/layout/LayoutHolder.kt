package com.x930073498.lib.layout

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.x930073498.lib.R
import com.x930073498.lib.finder.ActivityFinder

/**
 * Created by x930073498 on 2018/12/24.
 */
class LayoutHolder internal constructor(private val view: ViewProvider, private val helper: LayoutHelper, private val adapter: LayoutAdapter) {
    data class Wrapper(internal val id: String, internal val data: Any?, internal val item: LayoutItem)

    internal var lifecycleOwner: LifecycleOwner? = null
    private var hasAdd = false


    private val map by lazy {
        mutableMapOf<Int, View>()
    }
    private val observable by lazy {
        MutableLiveData<Wrapper>()
    }


    fun getContext(): Context {
        return getView().context
    }

    fun getActivity(): Activity? {
        return ActivityFinder.find(getView())
    }

    fun getAppCompatActivity(): AppCompatActivity? {
        return ActivityFinder.findCompat(getView())
    }

    internal fun push(id: String, data: Any?, item: LayoutItem) {
        addObservable()
        observable.postValue(Wrapper(id, data, item))
    }

    private fun addObservable() {
        if (hasAdd) return
        lifecycleOwner?.run {
            observable.observe(this, Observer {
                it?.run {
                    bind(this)
                }
            })
        } ?: observable.observeForever {
            it?.run { bind(this) }
        }
        hasAdd = true
    }

    @Suppress("UNCHECKED_CAST")
    private fun bind(wrapper: Wrapper) {
        wrapper.item.bind(helper, adapter, this, wrapper.data, wrapper.id, adapter.getItems())
    }

    fun getView(): View {
        return view.provideView()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(id: Int): T? {
        return (map[id] ?: getView().findViewById<T?>(id)?.apply {
            map[id] = this
        }) as? T
    }

    fun getLifecycleOwner(): LifecycleOwner? {
        return lifecycleOwner
    }

    fun addLifecycleObserver(observer: LifecycleObserver) {
        Log.e("addLifecycleObserver", "enter this line 22")
        lifecycleOwner?.lifecycle?.addObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getTags(): MutableMap<Any, Any> {
        return getView().getTag(R.id.holder_tag) as? MutableMap<Any, Any> ?: mutableMapOf()
    }

    fun setTag(key: Any, tag: Any?) {
        val map = getTags()
        if (tag == null) map.remove(key)
        else map[key] = tag
        getView().setTag(R.id.holder_tag, map)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getTag(key: Any, defaultValve: T): T {
        val map = getTags()
        return map[key] as? T ?: defaultValve
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getTag(key: Any, defaultValve: () -> T): T {
        val map = getTags()
        return map[key] as? T ?: defaultValve()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getTag(key: Any): T? {
        val map = getTags()
        return map[key] as? T
    }
}