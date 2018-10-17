package com.x930073498.lib.layout

import android.arch.lifecycle.LifecycleOwner
import android.view.View

/**
 * Created by x930073498 on 2018/10/11.
 */
class LayoutItemWrapper private constructor(var source: Any? = null, private val id: IdProvider, private val dataBinder: LayoutDataBinder? = null) : LayoutItem {

    override fun provideId(adapter: LayoutAdapter, helper: LayoutHelper, parent: View, data: Any?): Int {
        return id.provideId(adapter, helper, parent, data)
    }

    override fun bindData(adapter: LayoutAdapter, helper: LayoutHelper, holder: LayoutHolder, data: Any?, owner: LifecycleOwner?) {
        dataBinder?.bindData(adapter, helper, holder, data, owner)
    }

    override fun getData(): Any? {
        return this.source
    }

    companion object {
        fun <T> wrap(data: T?, id: IdProvider, dataBinder: LayoutDataBinder): LayoutItem {
            return LayoutItemWrapper(data, id, dataBinder)
        }

        fun <T> wrap(data: T?, item: LayoutItem): LayoutItem {
            return wrap(data, item, item)
        }

        fun <T> wrap(list: List<T>, id: IdProvider, dataBinder: LayoutDataBinder): List<LayoutItem> {
            val result = arrayListOf<LayoutItem>()
            list.forEach {
                result += wrap(it, id, dataBinder)
            }
            return result
        }

        fun <T> wrap(list: List<T>, item: LayoutItem): List<LayoutItem> {
            return wrap(list, item, item)
        }

    }
}