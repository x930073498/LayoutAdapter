package com.x930073498.lib.layout

import android.app.Activity
import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.Fragment
import android.view.View
import com.x930073498.lib.finder.LifecycleOwnerFinder

/**
 * Created by x930073498 on 2018/12/24.
 */
class LayoutHelper private constructor(private val view: ViewProvider, private val adapter: LayoutAdapter, private val hasLifecycle: Boolean) {

    private var id = System.currentTimeMillis().toString()

    init {
        adapter.setHelper(this)
    }

    fun setId(id: String) {
        this.id = id
    }

    fun pull() {
        container[id]?.let {
            container.remove(id)
            it.id?.run {
                push(this, it.data)
            } ?: push(it.data)
        }
    }

    private var tag: Any? = null


    fun getAdapter(): LayoutAdapter {
        return adapter
    }

    fun destroy() {
        tag = null
        list.remove(this)
    }

    fun push(id: String, data: Any?) {
        adapter.push(id, data)
    }

    fun push(data: Any?) {
        adapter.push(data)
    }

    fun register(id: String, item: LayoutItem) {
        adapter.register(id, item)
    }

    fun register(item: LayoutItem): String {
        return adapter.register(item)
    }

    fun unregister(item: LayoutItem) {
        adapter.unregister(item)
    }

    fun unregister(id: String) {
        adapter.unregister(id)
    }


    fun getId(item: LayoutItem): String? {
        return adapter.getId(item)
    }

    fun getId(): String {
        return id
    }

    internal fun getViewProvider(): ViewProvider {
        return view
    }

    internal fun getLifecycleOwner(): LifecycleOwner? {
        return tag as? LifecycleOwner ?: getInternalLifecycleOwner()
    }

    private fun getInternalLifecycleOwner(): LifecycleOwner? {
        return (tag as? View)?.run {
            LifecycleOwnerFinder.find(this)
        } ?: LifecycleOwnerFinder.find(view.provideView())
    }

    companion object {
        data class Data(internal val id: String?, internal val data: Any?)

        private val list by lazy {
            mutableListOf<LayoutHelper>()
        }
        private val container by lazy {
            mutableMapOf<String, Data>()
        }

        fun destroy(any: Any) {
            list.find { it.tag == any }?.destroy()
        }

        fun destroyById(id: String) {
            get(id)?.destroy()
        }

        fun push(helperId: String, id: String, data: Any?) {
            get(helperId)?.push(id, data) ?: container.put(helperId, Data(id, data))
        }

        fun push(helperId: String, data: Any?) {
            get(helperId)?.push(data) ?: container.put(helperId, Data(null, data))
        }

        fun pull(helperId: String) {
            get(helperId)?.pull()
        }

        fun push(helper: LayoutHelper, id: String, data: Any?) {
            helper.push(id, data)
        }

        fun push(helper: LayoutHelper, data: Any?) {
            helper.push(data)
        }

        @Synchronized
        fun attach(activity: Activity): LayoutHelper {
            return list.find { it.tag == activity } ?: LayoutHelper(object : ViewProvider {
                override fun provideView(): View {
                    return activity.findViewById<View>(android.R.id.content)
                }
            }, LayoutAdapter(), true).apply {
                if (activity is LifecycleOwner) {
                    activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            destroy()
                        }
                    })
                }
                tag = activity
                list.add(this)
            }
        }

        @Synchronized
        fun attach(view: View): LayoutHelper {
            return list.find { it.tag == view } ?: LayoutHelper(object : ViewProvider {
                override fun provideView(): View {
                    return view
                }
            }, LayoutAdapter(), true).apply {
                view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                    override fun onViewDetachedFromWindow(v: View?) {
                        destroy()
                    }

                    override fun onViewAttachedToWindow(v: View?) {
                    }

                })
                tag = view
                list.add(this)
            }
        }

        @Synchronized
        fun attach(fragment: Fragment): LayoutHelper {
            return list.find { it.tag == fragment } ?: LayoutHelper(object : ViewProvider {
                override fun provideView(): View {
                    return fragment.view as View
                }
            }, LayoutAdapter(), true).apply {
                fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        destroy()
                    }
                })
                tag = fragment
                list.add(this)
            }
        }

        @Synchronized
        fun attach(view: ViewProvider): LayoutHelper {
            return list.find { it.tag == view }
                    ?: LayoutHelper(view, LayoutAdapter(), false).apply {
                        tag = view
                        list.add(this)

                        getLifecycleOwner()?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                            override fun onDestroy(owner: LifecycleOwner) {
                                destroy()
                            }
                        })
                                ?: view.provideView().addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                                    override fun onViewDetachedFromWindow(v: View?) {
                                        destroy()
                                    }

                                    override fun onViewAttachedToWindow(v: View?) {
                                    }

                                })
                    }
        }

        fun get(id: String): LayoutHelper? {
            return list.find { it.id == id }
        }


    }
}