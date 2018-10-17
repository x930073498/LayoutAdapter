package com.x930073498.lib.recycler

import android.support.v7.widget.RecyclerView

import java.util.ArrayList

/**
 * Created by x930073498 on 2018/8/21.
 */

class BaseItemWrapper private constructor(private val data: Any?, private val layout: LayoutId, private val dataBinder: DataBinder? = null, private val visible: VisibleProvider? = null) : BaseItem {


    override fun bindData(adapter: RecyclerView.Adapter<*>, holder: CommonAdapter.CommonViewHolder, data: Any?, position: Int, source: List<BaseItem>) {
        dataBinder?.bindData(adapter, holder, data, position, source)
    }

    override fun getData(): Any? {
        return data

    }

    override fun visible(item: BaseItem, source: List<BaseItem>): Boolean {
        return visible?.visible(item, source) ?: true
    }

    override fun layout(position: Int, data: Any?, source: List<BaseItem>): Int {
        return layout.layout(position, data, source)
    }

    companion object {

        fun <T> wrap(data: T, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): BaseItem {
            return BaseItemWrapper(data, layout, dataBinder, visible)
        }

        fun <T> wrap(data: T, item: BaseItem): BaseItem {
            return BaseItemWrapper(data, item, item, item)
        }

        fun <T> wrap(data: List<T>?, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): List<BaseItem> {
            val result = ArrayList<BaseItem>()
            if (data == null) return result
            for (temp in data) {
                result.add(wrap(temp, layout, dataBinder, visible))
            }
            return result
        }

        fun <T> wrap(data: List<T>, item: BaseItem): List<BaseItem> {
            return wrap(data,item,item,item)
        }

        fun <T> wrapCheckType(data: List<T>?, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): List<BaseItem> {
            val result = ArrayList<BaseItem>()
            if (data == null) return result
            for (temp in data) {
                if (temp is BaseItem) {
                    result.add(temp)
                } else
                    result.add(wrap(temp, layout, dataBinder, visible))
            }
            return result
        }

        fun <T> wrapCheckType(data: List<T>?, item: BaseItem): List<BaseItem> {
            return wrapCheckType(data, item, item, item)
        }
    }
}
