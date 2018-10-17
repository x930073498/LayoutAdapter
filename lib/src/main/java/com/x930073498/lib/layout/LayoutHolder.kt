package com.x930073498.lib.layout

import android.view.View

/**
 * Created by x930073498 on 2018/10/11.
 */
class LayoutHolder internal constructor(private val parent: View?, val itemView: View?) {

    val map by lazy {
        mutableMapOf<Int, View?>()
    }

    fun getParent(): View? {
        return parent
    }

    inline fun <reified T : View> getContentView(): T? {
        return itemView as? T
    }

    inline fun <reified T : View> getView(id: Int): T? {
        var view = map[id]
        if (view != null && view is T) {
            return view
        }
        view = itemView?.findViewById(id)
        map[id] = view
        return view as? T
    }


}