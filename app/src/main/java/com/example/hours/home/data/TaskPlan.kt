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
    var cycleDays: Int = 0
    var cycleMtime: Int = 0
    var cycleMtimeDone: Int = 0
    var show: Boolean = false

    constructor(tid: Int, type: Int, dayOfWeek: Int, oneCycleDays: Int, oneCycleMtime: Int): this() {
        this.tid = tid
        this.type = type
        this.dayOfWeek = dayOfWeek
        this.cycleDays = oneCycleDays
        this.cycleMtime = oneCycleMtime

        var tsNow =(System.currentTimeMillis()/1000).toInt()
        var todayZeroTime = tsNow - (tsNow + 3600 * 8) % 86400

        // 根据type是按天还是按星期，设置
        when (type) {
            TYPE_WEEK -> {
                var todayOfWeek = Calendar.getInstance().also { it.time = Date() }.get(Calendar.DAY_OF_WEEK)
                this.startZeroTime = todayZeroTime + (dayOfWeek-todayOfWeek)*86400
            }
            else -> this.startZeroTime = todayZeroTime
        }
    }


    class PlanInfoOnly(var type: Int=TYPE_DAY, var cycleDays: Int=0, var cycleMtime: Int=0) {
        var tid = 0
        var cycleMtimeDone = 0
        var dayOfWeekList: ArrayList<Int> = ArrayList()

        fun getWeekBitsFromList(): Int {
            var weekBits = 0
            for (dayOfWeek in this.dayOfWeekList)
                weekBits += 1.shl(dayOfWeek)
            return weekBits
        }

        fun setListByWeekBits(weekBits: Int) {
            for (dayOfWeek in 1..7)
                if (weekBits.and(1.shl(dayOfWeek)) != 0)
                    dayOfWeekList.add(dayOfWeek)
        }

        fun setPlanInfoByTask(task: Task) {
            task?.let {
                tid = it.id!!
                type = it.planType
                cycleMtime = it.cycleMtime
                cycleDays = it.cycleDays
            }
            // 位运算判断星期1-7哪些存在
            if (type == TYPE_WEEK)
                setListByWeekBits(task.cycleWeekBits)
        }


        override fun toString(): String {
            val enNameList = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            val timeStr = "${this.cycleMtime/60}h ${this.cycleMtime%60}m"
            val everyStr = when(this.type) {
                TYPE_WEEK -> {
                    var text = "every "
                    this.dayOfWeekList.forEach {  text += "${enNameList[it-1]}," }
                    text
                }
                else -> "every ${this.cycleDays} day(s)"
            }

            return "$everyStr $timeStr"
        }

    }

}