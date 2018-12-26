package com.x930073498.lib.finder

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.TintContextWrapper
import android.view.View

/**
 * Created by x930073498 on 2018/10/19.
 */
object ActivityFinder {
    fun find(anchor: View?): Activity? {
        return find(anchor?.context)
    }

    fun find(context: Context?): Activity? {
        return scanForActivity(context)
    }

    fun findCompat(anchor: View?): AppCompatActivity? {
        return find(anchor) as? AppCompatActivity
    }

    fun findCompat(context: Context?): AppCompatActivity? {
        return find(context) as? AppCompatActivity
    }


    private fun scanForActivity(context: Context?): Activity? {
        return context as? Activity ?: (context as? TintContextWrapper)?.let {
            scanForActivity(it.baseContext)
        } ?: (context as? ContextWrapper)?.let {
            scanForActivity(it.baseContext)
        }
    }
}