package com.x930073498.layoutadapter

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.widget.TextView
import android.widget.Toast
import com.x930073498.lib.layout.LayoutAdapter
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutHolder
import com.x930073498.lib.layout.LayoutItem

/**
 * Created by x930073498 on 2018/12/25.
 */
class TestLayoutItem : LayoutItem {
    override fun filter(data: Any?): Boolean {
        return data is String
    }

    override fun bind(helper: LayoutHelper, adapter: LayoutAdapter, holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>) {
        if (data is String) {
            holder.getView<TextView>(R.id.tvMain)?.run {
                text = data
                setOnClickListener {
                    Toast.makeText(holder.getContext(), "测试", Toast.LENGTH_SHORT).show()
                    helper.push("1", arrayListOf(1))
                }
                holder.addLifecycleObserver(object :DefaultLifecycleObserver{
                    override fun onResume(owner: LifecycleOwner) {
                        Toast.makeText(holder.getContext(), "onResume", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


    }


}