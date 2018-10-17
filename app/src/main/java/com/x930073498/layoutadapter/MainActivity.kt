package com.x930073498.layoutadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.x930073498.lib.layout.LayoutHelper
import com.x930073498.lib.layout.LayoutItemWrapper

class MainActivity : AppCompatActivity() {
    private val mainTextItem by lazy {
        LayoutItemWrapper.wrap("first", HomeMainTextItem())
    }
    private val secondTextItem by lazy {
        LayoutItemWrapper.wrap("second", HomeSecondTextItem())
    }
    private val helper by lazy {
        LayoutHelper.attach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper.push(mainTextItem)
        helper.push(secondTextItem)
        setContentView(R.layout.activity_main)
    }

}
