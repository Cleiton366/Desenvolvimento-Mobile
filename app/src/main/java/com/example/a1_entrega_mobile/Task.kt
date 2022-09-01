package com.example.a1_entrega_mobile

import java.io.Serializable

class Task : Serializable {
    var taskId : String = ""
    var taskName : String = ""
    var taskEstimative : Int = 0
    var taskPriority : String = ""
    var taskNotification : Boolean = false

    fun editTask(taskName : String, taskEstimative : Int,
                 taskPriority : String, taskNotification : Boolean) {
        this.taskName = taskName
        this.taskEstimative = taskEstimative
        this.taskPriority = taskPriority
        this.taskNotification = taskNotification
    }

    constructor(taskNotification: Boolean,taskPriority: String,
                taskName: String, taskEstimative: Int, taskId : String) {
        this.taskName = taskName
        this.taskEstimative = taskEstimative
        this.taskPriority = taskPriority
        this.taskNotification = taskNotification
        this.taskId = taskId
    }

    override fun toString () : String {
        return "Task name:" + taskName + " Task Estimative:" + taskEstimative +
                " Task Priority:" + taskPriority + " is notification enable? " + taskNotification
    }
}