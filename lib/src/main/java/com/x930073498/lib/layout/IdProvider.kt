package com.x930073498.lib.layout

import android.support.annotation.IdRes
import android.view.View

/**
 * Created by x930073498 on 2018/10/11.
 */
interface IdProvider {
    @IdRes
    fun provideId(adapter: LayoutAdapter, helper: LayoutHelper,  parent: View,data:Any?):  Int
}