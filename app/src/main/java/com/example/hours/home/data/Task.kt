package com.example.hours.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
class Task(
    var name: String,
    var time: Int = 0,
    var drawableId: Int = 0) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor(id: Int, name: String, time: Int, drawableId: Int): this(name, time, drawableId) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        (other as Task)?.let {
            return ((id == it.id) && (name == it.name) && (drawableId == it.drawableId) && (time == it.time))
        }
        return false
    }
}

