package com.example.hours.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "Task_Plan")
class TaskPlan() {
    companion object {
        val TYPE_DAY = 0
        val TYPE_WEEK = 1
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var tid: Int = 0
    var type: Int = TYPE_DAY
    var dayOfWeek: Int = 0
    var startZeroTime: Int = 0
    var oneCycleDays: Int = 0
    var oneCycleMtime: Int = 0
    var oneCycleMtimeHasDone: Int = 0
    var show: Boolean = false

    constructor(tid: Int, type: Int, dayOfWeek: Int, oneCycleDays: Int, oneCycleMtime: Int): this() {
        this.tid = tid
        this.type = type
        this.dayOfWeek = dayOfWeek
        this.oneCycleDays = oneCycleDays
        this.oneCycleMtime = oneCycleMtime

        var tsNow =(System.currentTimeMillis()/1000).toInt()
        var todayZeroTime = tsNow - (tsNow + 3600 * 8) % 86400

        // 根据type是按天还是按星期，设置
        when (type) {
            TYPE_WEEK -> {
                var todayOfWeek = Calendar.getInstance().also { it.time = Date() }.get(Calendar.DAY_OF_WEEK)
                this.startZeroTime = todayZeroTime + (dayOfWeek-todayOfWeek)*86400
                this.oneCycleDays = 7
            }
            else -> this.startZeroTime = todayZeroTime
        }
    }


    class PlanInfoOnly(var type: Int=TYPE_DAY, var oneCycleDays: Int=0, var oneCycleMtime: Int=0) {

        var dayOfWeekList: ArrayList<Int> = ArrayList()

        override fun toString(): String {
            val enNameList = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            val timeStr = "${this.oneCycleMtime/60}h ${this.oneCycleMtime%60}m"
            val everyStr = when(this.type) {
                TYPE_WEEK -> {
                    var text = "every "
                    this.dayOfWeekList.forEach {  text += "${enNameList[it-1]}," }
                    text
                }
                else -> "every ${this.oneCycleDays} day(s)"
            }

            return "$everyStr $timeStr"
        }

    }

}