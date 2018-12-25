package com.x930073498.layoutadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.x930073498.lib.layout.LayoutHelper

class MainActivity : AppCompatActivity() {
    private val helper by lazy {
        LayoutHelper.attach(this).apply {
            register("",TestLayoutItem())
            register("1",TestIntListLayoutItem())
            register("2",TestStringListLayoutItem())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helper.push("测试2")

//        helper.unregister("")
        helper.push("测试3")
//        helper.push("2","测试3")
//        helper.push(arrayListOf("测试3"))

    }

}
