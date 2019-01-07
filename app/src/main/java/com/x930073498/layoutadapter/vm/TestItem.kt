package com.x930073498.layoutadapter.vm

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.x930073498.annotations.ItemId

import com.x930073498.layoutadapter.R
import com.x930073498.lib.recycler.BaseItem
import com.x930073498.lib.recycler.CommonAdapter

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
@ItemId("1")
class TestItem : BaseItem {

    override fun bindData(adapter: RecyclerView.Adapter<*>, holder: CommonAdapter.CommonViewHolder, data: Any?, position: Int, source: List<BaseItem>, payloads: List<Any>) {
        if (data is String) {
            holder.getView<TextView>(R.id.tv)?.text = data
        }

    }


    override fun layout(position: Int, data: Any?, source: List<BaseItem>): Int {
        return R.layout.layout_item_test
    }


}
