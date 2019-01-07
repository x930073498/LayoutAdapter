package com.x930073498.layoutadapter.vm

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.x930073498.layoutadapter.R
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

    private var count = 0

    override fun bind(helper: LayoutHelper, adapter: LayoutAdapter, holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>) {
        if (data is String) {
            holder.getView<TextView>(R.id.tvMain)?.let {
                it.text = data
                it.setOnClickListener {
                    helper.push("点击${count++}")
                }
                holder.setTag("data", data)
                holder.getTag<LifecycleObserver>(it){
                    object : DefaultLifecycleObserver {
                        override fun onResume(owner: LifecycleOwner) {
                            Toast.makeText(holder.getContext(), holder.getTag<String>("data"), Toast.LENGTH_SHORT).show()
                        }
                    }.apply {
                        holder.setTag(it, this)
                        holder.addLifecycleObserver(this)
                    }
                }
            }
        }


    }


}