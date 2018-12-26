package com.x930073498.layoutadapter.vm

import com.x930073498.lib.layout.LayoutAdapter
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutHolder
import com.x930073498.lib.layout.LayoutItem

/**
 * Created by x930073498 on 2018/12/25.
 */
class TestStringListLayoutItem : LayoutItem {
    override fun filter(data: Any?): Boolean {
        return data is List<*> && !data.any { it !is String }
    }

    override fun bind(helper: LayoutHelper, adapter: LayoutAdapter, holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>) {

    }


}