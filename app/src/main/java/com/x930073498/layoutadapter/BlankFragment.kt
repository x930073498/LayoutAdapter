package com.x930073498.layoutadapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x930073498.layoutadapter.vm.TestLayoutItem
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.ViewProvider


class BlankFragment : Fragment(),ViewProvider {
    override fun provideView(): View {
        return view!!
    }

    init {
        LayoutHelper.attach(this as ViewProvider).apply {
            register(TestLayoutItem())
            setId(HELPER_ID_BLANK_FRAGMENT)
            pull()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.e("enter","onResume")
    }
    companion object {
        @JvmStatic
        fun newInstance() =
                BlankFragment()
    }
}
