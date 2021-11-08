package com.androidkotlin.construct

class construct_create_task(
    type_task: String,
    name_task: String,
    date_task: String
){
    var type_task: String? = null
    var name_task: String? = null
    var date_task: String? = null
    init {

        this.type_task = type_task
        this.name_task = name_task
        this.date_task = date_task

    }
}
