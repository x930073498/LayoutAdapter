package com.x930073498.layoutadapter

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import android.widget.TextView
import com.x930073498.lib.layout.LayoutAdapter
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutHolder
import com.x930073498.lib.layout.LayoutItem

/**
 * Created by x930073498 on 2018/10/11.
 */
class HomeMainTextItem : LayoutItem {
    override fun provideId(adapter: LayoutAdapter, helper: LayoutHelper, parent: View, data: Any?): Int {
        return R.id.tvMain
    }

    private var count = 0
    override fun bindData(adapter: LayoutAdapter, helper: LayoutHelper, holder: LayoutHolder, data: Any?, owner: LifecycleOwner?) {
        if (data is String?) {
            val textView = holder.getContentView<TextView>()
            textView?.text = data
            textView?.setOnClickListener {
                helper.push(R.id.tvSecond, "点击${count++}次")
            }
//            helper.pushData("测试")
        }
    }

}