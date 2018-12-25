package com.x930073498.lib.layout

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.view.View
import com.x930073498.lib.R

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
        return view.getView()
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
        lifecycleOwner?.lifecycle?.addObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    fun setTag(key: String, tag: Any) {
        val map = (getView().getTag(R.id.layout_holder_tag) ?: mutableMapOf<String, Any>().apply {
            getView().setTag(R.id.layout_holder_tag, this)
        }) as MutableMap<String, Any>
        map[key] = tag
    }

    @Suppress("UNCHECKED_CAST")
    fun getTag(key: String): Any? {
        val map = (getView().getTag(R.id.layout_holder_tag) ?: mutableMapOf<String, Any>().apply {
            getView().setTag(R.id.layout_holder_tag, this)
        }) as MutableMap<String, Any>
        return map[key]
    }
}