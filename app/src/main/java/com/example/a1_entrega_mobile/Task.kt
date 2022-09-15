package com.example.a1_entrega_mobile

import java.io.Serializable

class Task : Serializable {
    var id : String = ""
    var name : String = ""
    var estimative : Int = 0
    var priority : Int = 0
    var notification : Boolean = false

    constructor(notification : Boolean,priority: Int,
                name: String, estimative: Int, id : String) {
        this.name = name
        this.estimative = estimative
        this.priority = priority
        this.notification= notification
        this.id = id
    }

}