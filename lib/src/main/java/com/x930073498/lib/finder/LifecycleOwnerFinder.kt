package com.x930073498.lib.finder

import android.app.Application
import android.app.Service
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by x930073498 on 2018/10/19.
 */
object LifecycleOwnerFinder {
    fun find(anchor: View?): LifecycleOwner? {
        return FragmentFinder.findFromAnchor(anchor) ?: ActivityFinder.findCompat(anchor)
    }

    fun find(context: Context?): LifecycleOwner? {
        if (context is Application || context is Service) {
            return null
        }
        return ActivityFinder.findCompat(context)
    }


}