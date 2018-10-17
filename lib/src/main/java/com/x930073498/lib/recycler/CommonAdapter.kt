package com.x930073498.lib.recycler

import android.content.Context
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x930073498.lib.R


/**
 * Created by x930073498 on 2018/8/20.
 */

class CommonAdapter : RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {

    private var data = arrayListOf<BaseItem>()

    private val visibleMap = ArrayMap<BaseItem, Boolean>()

    private var onDataBind: ((Int) -> Unit)? = null


    fun setBindListener(listener: (Int) -> Unit) {
        this.onDataBind = listener
    }

    private fun isItemVisible(item: BaseItem?): Boolean {
        if (item?.getData() == null) return false
        return visibleMap[item] == null || visibleMap[item]!! || item.visible(item, data)
    }


    companion object {
        const val TYPE_NO_LAYOUT = -1
    }

    fun setItemVisible(item: BaseItem, isVisible: Boolean) {
        visibleMap[item] = isVisible
    }

    val isEmpty: Boolean
        get() = data.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(if (viewType == TYPE_NO_LAYOUT) R.layout.layout_space else viewType, parent, false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val item = getItem(position)
        if (isItemVisible(item)) {
            holder.itemView.visibility = View.VISIBLE
            item?.bindData(this, holder, item.getData(), position, data)
        } else {
            holder.itemView.visibility = View.GONE
        }
        onDataBind?.invoke(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return TYPE_NO_LAYOUT
        if (!isItemVisible(item)) return TYPE_NO_LAYOUT
        return item.layout(position, item.getData(), data)
    }

    fun getItem(index: Int): BaseItem? {
        return data[index]
    }

    fun containsItem(item: BaseItem): Boolean {
        return data.contains(item)
    }

    fun containsData(data: Any): Boolean {
        this.data.forEach {
            if (data == it.getData()) return true
        }
        return false
    }

    fun getData(index: Int): Any? {
        return getItem(index)?.getData()
    }

    fun add(data: BaseItem?) {
        if (data == null) return
        this.data.add(data)
    }

    fun add(data: List<BaseItem>) {
        this.data.addAll(data)
    }

    fun insert(data: List<BaseItem>?, index: Int = 0) {
        if (data == null) return
        this.data.addAll(index, data)
    }

    fun insert(data: BaseItem?, index: Int) {
        if (data == null) return
        this.data.add(index, data)
    }

    fun insert(data: BaseItem?) {
        if (data == null) return
        insert(data, 0)
    }

    fun remove(data: BaseItem) {
        this.data.remove(data)
    }

    fun remove(data: List<BaseItem>?) {
        if (data == null) return
        this.data.removeAll(data)
    }

    fun clear() {
        data.clear()
    }


    fun getSource(): List<BaseItem> {
        return data.toList()
    }

    fun replace(data: BaseItem?, index: Int) {
        if (data == null) {
            this.data.removeAt(index)
            return
        }
        this.data.add(index, data)
        this.data.removeAt(index + 1)
    }


    fun moveOrAdd(data: BaseItem, to: Int) {
        val index = this.data.indexOf(data)
        if (index < 0) {
            this.data.add(to, data)
            return
        } else
            move(index, to)
    }

    fun move(index: Int, to: Int) {
        if (index < 0) return
        val data = this.data[index]
        if (index == to) return
        if (to >= this.data.size) return
        this.data.add(to, data)
        if (index < to) {
            this.data.removeAt(index)
        } else {
            this.data.removeAt(index + 1)
        }

    }

    fun replace(data: List<BaseItem>?) {
        if (data == null)
            this.data.clear()
        else {
            this.data.clear()
            this.data.addAll(data)
        }
    }

    fun indexOf(data: BaseItem): Int {
        return this.data.indexOf(data)
    }

    open class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var map: ArrayMap<Int, View>? = null
        private val DEFAULT_KEY by lazy {
            "default_key"
        }
        private val tags by lazy {
            ArrayMap<Any, Any?>()
        }

        fun setTag(tag: Any) {
            tags[DEFAULT_KEY] = tag
        }

        fun setTag(key: Any, tag: Any?) {
            if (tag == null) tags.remove(key)
            else tags[key] = tag
        }

        fun getTag(): Any? {
            return tags[DEFAULT_KEY]
        }

        fun getTag(key: Any): Any? {
            return tags[key]
        }

        @Suppress("UNCHECKED_CAST")
        fun <T : View> getView(id: Int): T? {
            if (map == null) map = ArrayMap()
            var view: View? = map!![id]
            if (view == null) {
                view = itemView.findViewById(id)
                if (view != null) map!![id] = view
            }
            return view as T?
        }

        fun getContext(): Context {
            return itemView.context
        }

        fun getRecyclerView(): RecyclerView? {
            return itemView.parent as? RecyclerView
        }
    }
}
