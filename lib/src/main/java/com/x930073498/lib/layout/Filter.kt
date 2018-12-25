package com.x930073498.lib.layout

/**
 * Created by x930073498 on 2018/12/25.
 */
interface Filter {
    /**
     * 返回true代表不进行下一步操作
     */
    fun filter(data: Any?): Boolean
}