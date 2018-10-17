package com.x930073498.lib.layout

import android.arch.lifecycle.LifecycleOwner

/**
 * Created by x930073498 on 2018/10/11.
 */
interface LayoutDataBinder {
    fun bindData(adapter: LayoutAdapter, helper: LayoutHelper,  holder: LayoutHolder, data: Any?, owner: LifecycleOwner?)
}