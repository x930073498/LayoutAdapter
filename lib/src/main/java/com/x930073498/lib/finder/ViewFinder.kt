package com.x930073498.lib.finder

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import java.util.function.Predicate

@Suppress("UNCHECKED_CAST")
/**
 * Created by x930073498 on 2018/9/29.
 */
object ViewFinder {

    fun <T : View> findFromAnchor(anchor: View?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (anchor == null) return null
        if (clazz.isInstance(anchor) && filter.test(anchor as T))
            return anchor
        val parent = anchor.parent
        if (clazz.isInstance(parent) && filter.test(parent as T)) return parent
        if (parent is View)
            return findFromAnchor(parent as View, clazz, filter)
        return null
    }
   inline fun <reified T : View> findFromAnchor(anchor: View?,filter: Predicate<T> = Predicate { true }): T? {
        return findFromAnchor(anchor,T::class.java,filter)
    }

    fun <T : View> findFromParent(parent: View?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (parent == null) return null
        if (clazz.isInstance(parent) && filter.test(parent as T))
            return parent
        return parent.let { it as? ViewGroup }?.let {
            getInternal(it, clazz, filter)
        }
    }

    fun <T : View> findAllFromParent(parent: View?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): List<T> {
        val des = arrayListOf<T>()
        if (parent == null) return des
        if (parent is ViewGroup) {
            findAllFromParentInternal(arrayListOf<ViewGroup>().apply {
                add(parent)
            }, arrayListOf(), des, 0, clazz, filter)
        } else if (clazz.isInstance(parent) && filter.test(parent as T)) {
            des.add(parent)
        }
        return des
    }

    private fun <T : View> findAllFromParentInternal(views: List<ViewGroup>, source: MutableList<ViewGroup>, des: MutableList<T>, index: Int, clazz: Class<T>, filter: Predicate<T> = Predicate { true }) {
        if (views.isEmpty()) return
        if (index >= views.size) return findAllFromParentInternal(source, arrayListOf(), des, 0, clazz, filter)
        val parent = views[index]
        if (clazz.isInstance(parent) && filter.test(parent as T)) {
            des.add(parent)
        }
        for (position in 0 until parent.childCount) {
            val temp = parent.getChildAt(position)
            if (temp is ViewGroup) {
                source.add(temp)
            } else if (clazz.isInstance(temp) && filter.test(temp as T)) {
                des.add(temp)
            }
        }
        findAllFromParentInternal(views, source, des, index + 1, clazz, filter)
    }

    inline fun <reified T : View> findAllFromParent(parent: View?, filter: Predicate<T> = Predicate { true }): List<T> {
        return findAllFromParent(parent, T::class.java, filter)
    }

    inline fun <reified T : View> findFromParent(parent: View?, filter: Predicate<T> = Predicate { true }): T? {
        return findFromParent(parent, T::class.java, filter)
    }


    fun <T : View> findFromActivity(activity: Activity?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (activity == null) return null
        return findFromParent(activity.window.decorView as? ViewGroup, clazz, filter)
    }

    fun <T : View> findAllFromActivity(activity: Activity?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): List<T> {
        if (activity == null) return arrayListOf()
        return findAllFromParent(activity.window.decorView as? ViewGroup, clazz, filter)
    }


    fun <T : View> findFromFragment(fragment: Fragment?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (fragment == null) return null
        return findFromParent(fragment.view as? ViewGroup, clazz, filter)
    }

    fun <T : View> findAllFromFragment(fragment: Fragment?, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): List<T> {
        if (fragment == null) return arrayListOf()
        return findAllFromParent(fragment.view as? ViewGroup, clazz, filter)
    }

    inline fun <reified T : View> findFromActivity(activity: Activity?, filter: Predicate<T> = Predicate { true }): T? {
        return findFromActivity(activity, T::class.java, filter)
    }

    inline fun <reified T : View> findAllFromActivity(activity: Activity?, filter: Predicate<T> = Predicate { true }): List<T> {
        return findAllFromActivity(activity, T::class.java, filter)
    }

    inline fun <reified T : View> findFromLifeOwner(owner: LifecycleOwner?, filter: Predicate<T> = Predicate { true }): T? {
        when (owner) {
            is Activity -> return findFromActivity(owner, T::class.java, filter)
            is Fragment -> return findFromFragment(owner, T::class.java, filter)
        }
        return null
    }
    inline fun <reified T : View> findAllFromLifeOwner(owner: LifecycleOwner?, filter: Predicate<T> = Predicate { true }): List<T>{
        when (owner) {
            is Activity -> return findAllFromActivity(owner, T::class.java, filter)
            is Fragment -> return findAllFromFragment(owner, T::class.java, filter)
        }
        return arrayListOf()
    }

    inline fun <reified T : View> findFromFragment(fragment: Fragment?, filter: Predicate<T> = Predicate { true }): T? {
        return findFromFragment(fragment, T::class.java, filter)
    }
    inline fun <reified T : View> findAllFromFragment(fragment: Fragment?, filter: Predicate<T> = Predicate { true }):List<T>{
        return findAllFromFragment(fragment, T::class.java, filter)
    }


    private fun <T : View> getInternal(parent: ViewGroup, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (clazz.isInstance(parent)) return parent as T
        else {
            var child: View
            val list = ArrayList<ViewGroup>()
            for (index in 0 until parent.childCount) {
                child = parent.getChildAt(index)
                if (clazz.isInstance(child) && filter.test(child as T)) return child
                else if (child is ViewGroup) {
                    list.add(child)
                }
            }
            return if (list.isEmpty()) null
            else {
                getInternal(list, arrayListOf(), 0, clazz, filter)
            }
        }
    }

    private fun <T : View> getInternal(views: List<ViewGroup>, source: MutableList<ViewGroup>, position: Int, clazz: Class<T>, filter: Predicate<T> = Predicate { true }): T? {
        if (views.isEmpty()) return null
        if (position >= views.size) {
            return getInternal(source, arrayListOf(), 0, clazz, filter)
        }
        val parent = views[position]
        if (clazz.isInstance(parent) && filter.test(parent as T)) return parent
        for (index in 0 until parent.childCount) {
            val temp = parent.getChildAt(index)
            if (clazz.isInstance(temp) && filter.test(temp as T)) return temp
            if (temp is ViewGroup) {
                source.add(temp)
            }
        }
        return getInternal(views, source, position + 1, clazz)

    }
}