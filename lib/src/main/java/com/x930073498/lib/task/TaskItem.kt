package com.x930073498.lib.task

/**
 * Created by x930073498 on 2018/12/26.
 * 单个任务项
 */
interface TaskItem {

    fun onReceive(data: Any?, lastTaskId: String): Boolean

    fun excute()

    fun onResult(data: Any?)
}