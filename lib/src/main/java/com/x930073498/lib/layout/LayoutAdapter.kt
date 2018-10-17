package com.x930073498.lib.layout

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.view.View

/**
 * Created by x930073498 on 2018/10/11.
 */
class LayoutAdapter {
    private lateinit var helper: LayoutHelper
    private var owner: LifecycleOwner? = null
    private val binders by lazy {
        mutableListOf<Binder>()
    }

    fun getCount(): Int {
        return binders.size
    }

    fun getItemPosition(item: LayoutItem): Int {
        binders.forEachIndexed { index, binder ->
            if (binder.item == item) return index
        }
        return -1
    }

    fun getData(index: Int): Any? {
        if (index < 0 || index >= binders.size) return null
        return binders[index].getData()
    }

    fun getHolder(index: Int): LayoutHolder? {
        if (index >= binders.size || index < 0) return null
        return binders[index].holder
    }


    internal fun setLifecycle(owner: LifecycleOwner) {
        this.owner = owner
    }

    internal fun push(id: Int, data: Any?) {
        binders.forEach {
            if (id == it.getId()) {
                it.set(data)
                return@forEach
            }
        }
    }

    internal fun pushIndex(index: Int, data: Any?) {
        if (index < 0 || index >= binders.size) return
        push(binders[index], data)
    }

    internal fun push() {
        binders.forEach {
            it.set()
        }
    }


    internal fun push(item: LayoutItem) {
        binders.forEach {
            if (item == it.item) {
                it.set(item.getData())
                return
            }
        }
        binders.add(Binder(item, owner).apply { set() })
    }

    internal fun push(item: LayoutItem, data: Any?) {
        binders.forEach {
            if (item == it.item) {
                it.set(data)
                return
            }
        }
        binders.add(Binder(item, owner).apply { set(data) })
    }

    internal fun <T> pushData(data: T, clazz: Class<T>) {
        binders.forEach {
            if (clazz.isInstance(it.getData())) {
                it.set(data)
            }
        }
    }

    internal fun setHelper(helper: LayoutHelper) {
        this.helper = helper
        if (helper.getView() == null) return
    }

    inner class Binder constructor(internal val item: LayoutItem, owner: LifecycleOwner? = null) : LayoutItem, Comparable<Binder> {

        internal var type: Class<*>? = null
        private fun getType() {
            type = type ?: liveData.value?.javaClass
        }

        override fun compareTo(other: Binder): Int {
            return getId().compareTo(other.getId())
        }


        override fun hashCode(): Int {
            return getId()
        }

        override fun equals(other: Any?): Boolean {
            return other is Binder && other.getId() == getId()
        }

        override fun provideId(adapter: LayoutAdapter, helper: LayoutHelper, parent: View, data: Any?): Int {
            return item.provideId(adapter, helper, parent, data)
        }

        override fun bindData(adapter: LayoutAdapter, helper: LayoutHelper, holder: LayoutHolder, data: Any?, owner: LifecycleOwner?) {
            item.bindData(adapter, helper, holder, data, owner)
        }

        override fun getData(): Any? {
            return liveData.value
        }

        private val liveData = MutableLiveData<Any>()
        internal val holder by lazy {
            createHolder()
        }

        init {
            if (owner != null) {
                liveData.observe(owner, Observer {
                    getType()
                    bindData(this@LayoutAdapter, helper, holder, it, owner)
                })
            } else {
                liveData.observeForever {
                    getType()
                    bindData(this@LayoutAdapter, helper, holder, it, owner)
                }
            }
        }

        private fun createHolder(): LayoutHolder {
            val parent = helper.getView()
            val view = parent?.findViewById<View>(item.provideId(this@LayoutAdapter, helper, parent, getData()))
            return LayoutHolder(parent, view)
        }

        internal fun post(data: Any?) {
            liveData.postValue(data)
        }

        internal fun post() {
            post(item.getData())
        }

        internal fun set(data: Any?) {
            liveData.value = data
        }

        internal fun set() {
            set(item.getData())
        }


        internal fun getId(): Int {
            val parent = holder.getParent() ?: return 0
            return provideId(this@LayoutAdapter, helper, parent, getData())
        }
    }
}