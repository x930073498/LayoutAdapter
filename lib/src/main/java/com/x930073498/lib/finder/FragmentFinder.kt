package com.x930073498.lib.finder

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by x930073498 on 2018/10/19.
 */
object FragmentFinder {
    fun findFromAnchor(anchor: View?): Fragment? {
        return with(anchor?.context ?: return null) {
            findFragmentFromViewPager(anchor)
                    ?: getFragmentFromFragmentManger(ActivityFinder.findCompat(this), anchor)
        }
    }

    fun findFromContext(context: Context?): Fragment? {
        return with(ActivityFinder.findCompat(context) ?: return null) {
            findFragmentByViewPager(ViewFinder.findFromActivity(this as Activity))
                    ?: supportFragmentManager?.fragments?.asReversed()?.find {
                        isSupportVisible(it)
                    }
        }
    }

    fun findFromFragment(fragment: Fragment?): Fragment? {
        return findFragmentByViewPager(ViewFinder.findFromFragment(fragment))
                ?: fragment?.fragmentManager
                        ?.fragments?.asReversed()
                        ?.find { isSupportVisible(it) }
                ?: fragment
    }


    private fun findFragmentFromViewPager(view: View): Fragment? {
        return findFragmentByViewPager(findViewPager(view))
    }


    private fun findFragmentByViewPager(viewPager: ViewPager?): Fragment? {
        return viewPager?.let {
            with(it.adapter) {
                when (this) {
                    is FragmentStatePagerAdapter -> getItem(viewPager.currentItem)
                    is FragmentPagerAdapter -> getItem(viewPager.currentItem)
                    else -> null
                }
            }.run {
                findFromFragment(this) ?: this
            } ?: (viewPager.parent as? View)?.run {
                findFragmentByViewPager(findViewPager(this))
            }
        }
    }

    private fun findViewPager(parent: View): ViewPager? {
        return ViewFinder.findFromAnchor(parent)
    }

    private fun getFragmentFromFragmentManger(activity: AppCompatActivity?, view: View): Fragment? {
        return activity?.supportFragmentManager?.fragments
                ?.asReversed()
                ?.find { fragment ->
                    with(fragment) {
                        isSupportVisible(this) && (this.view?.let { isParentValidate(it, view) }
                                ?: false)
                    }
                }
    }

    private fun isParentValidate(root: View, view: View): Boolean {
        return root == view || with(root.parent) {
            this == view || (this as? View)?.let {
                isParentValidate(root, it)
            } ?: false
        }
    }

    private fun isSupportVisible(fragment: Fragment): Boolean {
        return fragment.parentFragment?.let {
            fragment.userVisibleHint && isSupportVisible(it)
        } ?: fragment.userVisibleHint
    }

}