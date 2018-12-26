package com.x930073498.layoutadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.x930073498.layoutadapter.vm.FragmentLayoutItem
import com.x930073498.layoutadapter.vm.TestLayoutItem
import com.x930073498.lib.layout.LayoutHelper

class MainActivity : AppCompatActivity() {
    init {
        LayoutHelper.push(HELPER_ID_MAIN_ACTIVITY, FragmentLayoutItem.FLAG)
        LayoutHelper.push(HELPER_ID_BLANK_FRAGMENT, "测试")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LayoutHelper.attach(this).apply {
            register("3", FragmentLayoutItem())
//            register("4", TestLayoutItem())
            setId(HELPER_ID_MAIN_ACTIVITY)
            pull()
        }
        setContentView(R.layout.activity_main)

    }

}
