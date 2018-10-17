package com.x930073498.lib.recycler

import android.support.v7.widget.RecyclerView

/**
 * Created by x930073498 on 2018/8/20.
 */

interface BaseItem : LayoutId, DataBinder, DataProvider, VisibleProvider {

    override fun bindData(adapter: RecyclerView.Adapter<*>, holder: CommonAdapter.CommonViewHolder, data: Any?, position: Int, source: List<BaseItem>) {}


}
