package com.x930073498.lib.recycler

import android.support.v7.widget.RecyclerView

/**
 * Created by x930073498 on 2018/8/21.
 */

class BaseItemWrapper private constructor(private val data: Any, private val layout: LayoutId, private val dataBinder: DataBinder? = null, private val visible: VisibleProvider? = null) : BaseItem {


    override fun bindData(adapter: RecyclerView.Adapter<*>, holder: CommonAdapter.CommonViewHolder, data: Any?, position: Int, source: List<BaseItem>, payloads: List<Any>) {
        dataBinder?.bindData(adapter, holder, data, position, source,payloads)
    }

    override fun getData(): Any {
        return data

    }

    override fun visible(item: BaseItem, source: List<BaseItem>): Boolean {
        return visible?.visible(item, source) ?: true
    }

    override fun layout(position: Int, data: Any?, source: List<BaseItem>): Int {
        return layout.layout(position, data, source)
    }

    override fun toString(): String {
        return "BaseItemWrapper(data=$data, layout=$layout, dataBinder=$dataBinder, visible=$visible)"
    }

    companion object {

        fun <T:Any> wrap(data: T, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): BaseItem {
            return BaseItemWrapper(data, layout, dataBinder, visible)
        }

        fun <T:Any> wrap(data: T, item: BaseItem): BaseItem {
            return BaseItemWrapper(data, item, item, item)
        }

        fun <T:Any> wrap(data: List<T>, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): List<BaseItem> {
            return data.map { wrap(it,layout, dataBinder,visible) }
        }

        fun <T:Any> wrap(data: List<T>, item: BaseItem): List<BaseItem> {
            return wrap(data,item,item,item)
        }

        fun <T:Any> wrapCheckType(data: List<T>, layout: LayoutId, dataBinder: DataBinder? = null, visible: VisibleProvider? = null): List<BaseItem> {
            return data.map {
                if (it is BaseItem) it
                else wrap(it, layout, dataBinder, visible)
            }
        }

        fun <T:Any> wrapCheckType(data: List<T>, item: BaseItem): List<BaseItem> {
            return wrapCheckType(data, item, item, item)
        }
    }

}
