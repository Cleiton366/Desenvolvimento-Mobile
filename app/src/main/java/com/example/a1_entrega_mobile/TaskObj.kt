package com.example.a1_entrega_mobile

import java.io.Serializable

class TaskObj : Serializable {
    var taskList : MutableList<Task>
    var task : Task
    var taskPosition : Int

    constructor(taskList : MutableList<Task>, task : Task, taskPosition : Int) {
        this.taskList = taskList
        this.task = task
        this.taskPosition = taskPosition
    }
}