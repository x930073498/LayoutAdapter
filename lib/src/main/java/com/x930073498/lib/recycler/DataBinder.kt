package com.x930073498.lib.recycler

import android.support.v7.widget.RecyclerView
import com.x930073498.lib.recycler.BaseItem
import com.x930073498.lib.recycler.CommonAdapter

/**
 * Created by x930073498 on 2018/8/20.
 */

interface DataBinder {

    fun bindData(adapter:  RecyclerView.Adapter<*>, holder: CommonAdapter.CommonViewHolder, data: Any?, position: Int, source: List<BaseItem>)
}
