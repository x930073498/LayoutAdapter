package com.x930073498.layoutadapter.vm

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.x930073498.layoutadapter.R
import com.x930073498.lib.layout.LayoutAdapter
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutHolder
import com.x930073498.lib.layout.LayoutItem
import com.x930073498.lib.recycler.BaseItemWrapper
import com.x930073498.lib.recycler.CommonAdapter
import com.x930073498.lib.recycler.BaseItemRepository

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
class RecyclerLayoutItem : LayoutItem {

    override fun filter(data: Any?): Boolean {
        return data is List<*> && data.all { it is String }
    }

    override fun bind(helper: LayoutHelper, adapter: LayoutAdapter, holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>) {
        holder.getTag(this) {
            CommonAdapter().apply {
                holder.getView<RecyclerView>(R.id.recycler)?.run {
                    setAdapter(this@apply)
                    layoutManager = LinearLayoutManager(holder.getContext())
                }

            }
        }.apply {
            @Suppress("UNCHECKED_CAST")
            replace(BaseItemWrapper.wrap(data as List<String>, BaseItemRepository.create("1")))
            notifyDataSetChanged()
        }
    }
}