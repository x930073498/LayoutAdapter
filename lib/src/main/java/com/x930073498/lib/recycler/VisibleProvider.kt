package com.x930073498.lib.recycler



/**
 * Created by x930073498 on 2018/9/13.
 */
interface VisibleProvider {

    fun visible(item: BaseItem, source: List<BaseItem>): Boolean {
        return true
    }
}