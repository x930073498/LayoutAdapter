package com.x930073498.lib.recycler



/**
 * Created by x930073498 on 2018/8/20.
 */

interface LayoutId {

    fun layout(position: Int, data: Any?, source: List<BaseItem>): Int
}
