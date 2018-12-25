package com.x930073498.lib.layout

/**
 * Created by x930073498 on 2018/12/25.
 */
interface DataBinder{
    fun bind(helper: LayoutHelper, adapter: LayoutAdapter,holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>)
}