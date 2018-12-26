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
        val helper = LayoutHelper.attach(view).apply {
            register("", TestLayoutItem())
            register("1", TestIntListLayoutItem())
            register("2", TestStringListLayoutItem())
        }
        helper.pull()
        helper.setId("test")
        helper.pull()



//        helper.pull("测试2")

//        helper.unregister("")
//        helper.pull("测试3")
//        helper.pull("2","测试3")
//        helper.pull(arrayListOf("测试3"))
    }

}
