package com.x930073498.lib.layout

/**
 * Created by x930073498 on 2018/12/24.
 */
class LayoutAdapter internal constructor() {

    private lateinit var helper: LayoutHelper
    private val map by lazy {
        mutableMapOf<String, LayoutItem>()
    }
    private val holderMap by lazy {
        mutableMapOf<String, LayoutHolder>()
    }

    internal fun setHelper(helper: LayoutHelper) {
        this.helper = helper
    }

    internal fun getItems(): List<LayoutItem> {
        return map.map { it.value }
    }
    fun getIds():List<String>{
       return map.map { it.key }
    }

    @Suppress("UNCHECKED_CAST")
    internal fun push(id: String, data: Any?) {
        map[id]?.let {
            it.takeIf {
                it.filter(data)
            }?.run { getHolder(id).push(id, data, it) }

        }
    }


    private fun getHolder(id: String): LayoutHolder {
        return holderMap[id] ?: LayoutHolder(helper.getViewProvider(), helper, this).apply {
            lifecycleOwner = helper.getLifecycleOwner()
        }
    }


    @Suppress("UNCHECKED_CAST")
    internal fun push(data: Any?) {
        map.filter { it.value.filter(data) }
                .map { getHolder(it.key).push(it.key, data, it.value) }
    }

    internal fun register(id: String, item: LayoutItem) {
        map[id] = item
        holderMap[id] = LayoutHolder(helper.getViewProvider(), helper, this).apply {
            lifecycleOwner = helper.getLifecycleOwner()
        }
    }

    @Synchronized
    internal fun register(item: LayoutItem): String {
        val id = System.currentTimeMillis().toString()
        map[id] = item
        holderMap[id] = LayoutHolder(helper.getViewProvider(), helper, this).apply {
            lifecycleOwner = helper.getLifecycleOwner()
        }
        return id
    }

    internal fun getId(item: LayoutItem): String? {
        return map.asIterable().find { it.value == item }?.key
    }

    internal fun unregister(id: String) {
        map.remove(id)
    }

    internal fun unregister(item: LayoutItem) {
        val id = getId(item) ?: return
        map.remove(id)
    }


}