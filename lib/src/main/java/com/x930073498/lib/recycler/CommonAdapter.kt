package com.x930073498.lib.recycler

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x930073498.lib.R
import com.x930073498.lib.finder.ActivityFinder
import com.x930073498.lib.finder.LifecycleOwnerFinder
import org.jetbrains.anko.attempt
import java.lang.ref.WeakReference


/**
 * Created by x930073498 on 2018/8/20.
 */

class CommonAdapter : RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {
    override fun onBindViewHolder(p0: CommonViewHolder, p1: Int) {
        onBindViewHolder(p0, p1, arrayListOf())
    }

    private var data = arrayListOf<BaseItem>()

    private val visibleMap = ArrayMap<BaseItem, Boolean>()

    private var onDataBind: ((CommonViewHolder, Int) -> Unit)? = null


    fun setBindListener(listener: (CommonViewHolder, Int) -> Unit) {
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
        return let {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(if (viewType == TYPE_NO_LAYOUT) R.layout.layout_space else viewType, parent, false)
            CommonViewHolder(view, parent)
        }


    }

    fun notifyData(data: Any) {
        this.data.withIndex().filter { data == it.value.getData() }.forEach { notifyItemChanged(it.index) }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = getItem(position)
        if (isItemVisible(item)) {
            holder.itemView.visibility = View.VISIBLE
            attempt { item?.bindData(this, holder, item.getData(), position, data, payloads) }.error?.printStackTrace()
        } else {
            holder.itemView.visibility = View.GONE
        }
        onDataBind?.invoke(holder, position)
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
        return this.data.any { data == it.getData() }
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

    fun remove(index: Int) {
        this.data.removeAt(index)
    }

    fun remove(data: List<BaseItem>?) {
        if (data == null) return
        this.data.removeAll(data)
    }

    fun clear() {
        data.clear()
    }

    fun getData(): List<BaseItem> {
        return data.toList()
    }

    fun getSource(): List<Any> {
        return data.map { it.getData() }
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

    open class CommonViewHolder(itemView: View, parent: ViewGroup? = null) : RecyclerView.ViewHolder(itemView) {
        private var parentRef: WeakReference<View>? = null
        private var activityRef: WeakReference<Activity>? = null
        private var lifecycleOwnerRef: WeakReference<LifecycleOwner>? = null

        init {
            parent?.let {
                parentRef = WeakReference(parent)
                val activity = ActivityFinder.find(parent)
                if (activity != null) activityRef = WeakReference(activity)
                val lifecycleOwner = LifecycleOwnerFinder.find(parent)
                if (lifecycleOwner != null) lifecycleOwnerRef = WeakReference(lifecycleOwner)
            }
        }

        private val map by lazy {
            mutableMapOf<Int, View>()
        }

        @Suppress("UNCHECKED_CAST")
        private fun getTags(): MutableMap<Any, Any> {
            return itemView.getTag(R.id.holder_tag) as? MutableMap<Any, Any> ?: mutableMapOf()
        }

        fun setTag(key: Any, tag: Any?) {
            val map = getTags()
            if (tag == null) map.remove(key)
            else map[key] = tag
            itemView.setTag(R.id.holder_tag, map)
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> getTag(key: Any, defaultValve: T): T {
            val map = getTags()
            return map[key] as? T ?: defaultValve
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> getTag(key: Any): T? {
            val map = getTags()
            return map[key] as? T
        }

        fun getLifecycleOwner(): LifecycleOwner? {
            return lifecycleOwnerRef?.get() ?: LifecycleOwnerFinder.find(parentRef?.get()).apply {
                if (this != null) {
                    lifecycleOwnerRef = WeakReference(this)
                }
            }
        }

        fun addLifeCycleObserver(observer: LifecycleObserver) {
            getLifecycleOwner()?.lifecycle?.addObserver(observer)
        }

        fun getActivity(): Activity? {
            return activityRef?.get() ?: ActivityFinder.find(parentRef?.get()).apply {
                if (this != null) {
                    activityRef = WeakReference(this)
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T : View> getView(id: Int): T? {
            var view: View? = map[id]
            if (view == null) {
                view = itemView.findViewById(id)
                if (view != null) map[id] = view
            }
            return view as? T
        }

        fun getContext(): Context {
            return itemView.context
        }


        fun getRecyclerView(): RecyclerView? {
            return parentRef?.get() as? RecyclerView
        }
    }
}
