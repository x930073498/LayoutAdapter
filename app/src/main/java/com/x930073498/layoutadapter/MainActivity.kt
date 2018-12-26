package com.x930073498.layoutadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.x930073498.lib.layout.LayoutHelper

class MainActivity : AppCompatActivity() {
    init {
        LayoutHelper.push("test", "测试3")
    }

    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         view = layoutInflater.inflate(R.layout.activity_main, null, false)
        setContentView(view)
        LayoutHelper.attach(view).apply {
            register("", TestLayoutItem())
            register("1", TestIntListLayoutItem())
            register("2", TestStringListLayoutItem())
        }.setId("test")
//        helper.push("测试2")

//        helper.unregister("")
//        helper.push("测试3")
//        helper.push("2","测试3")
//        helper.push(arrayListOf("测试3"))

    }

    override fun onResume() {
        super.onResume()

    }
}
