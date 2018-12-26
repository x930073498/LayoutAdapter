package com.x930073498.layoutadapter.vm

import com.x930073498.layoutadapter.BlankFragment
import com.x930073498.layoutadapter.HELPER_ID_BLANK_FRAGMENT
import com.x930073498.layoutadapter.R
import com.x930073498.lib.layout.LayoutAdapter
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutHolder
import com.x930073498.lib.layout.LayoutItem

/**
 * Created by x930073498 on 2018/12/26.
 */
class FragmentLayoutItem : LayoutItem {
    private var hasBind = false

    companion object {
        const val FLAG = "FragmentLayoutItem"
    }

    override fun filter(data: Any?): Boolean {
        return data == FLAG
    }

    override fun bind(helper: LayoutHelper, adapter: LayoutAdapter, holder: LayoutHolder, data: Any?, id: String, items: List<LayoutItem>) {
        if (hasBind) return
        holder.getAppCompatActivity()?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, BlankFragment.newInstance())?.commit()
        hasBind = true
        holder.getView().postDelayed({
            LayoutHelper.push(HELPER_ID_BLANK_FRAGMENT, "进入")
        }, 3000)
    }
}