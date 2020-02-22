package com.example.hours.home.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
class Task(): Parcelable {

    // basic info
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String = ""
    var drawableId: Int = 0
    var totalMtime: Int = 0
    var totalMtimeDone: Int = 0
    var addTime: Int = (System.currentTimeMillis()/1000).toInt()
    var note: String = ""
    //var isToday: Boolean = false
    // task plan, can in a new table
    var planType: Int = TaskPlan.TYPE_DAY
    var cycleWeekBits: Int = 0
    var startZeroTime: Int = 0
    var cycleDays: Int = 0
    var cycleMtime: Int = 0
    var cycleMtimeDone: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString().toString()
        drawableId = parcel.readInt()
        totalMtime = parcel.readInt()
        totalMtimeDone = parcel.readInt()
        addTime = parcel.readInt()
        note = parcel.readString().toString()

        planType = parcel.readInt()
        cycleWeekBits = parcel.readInt()
        cycleDays = parcel.readInt()
        startZeroTime = parcel.readInt()
        cycleMtime = parcel.readInt()
        cycleMtimeDone = parcel.readInt()
    }


    constructor(name: String, mtime: Int, drawableId: Int, note: String=""): this() {
        this.name = name
        this.totalMtime = mtime
        this.drawableId = drawableId
        this.note = note

        var tsNow =(System.currentTimeMillis()/1000).toInt()
        var todayZeroTime = tsNow - (tsNow + 3600 * 8) % 86400
        this.startZeroTime = todayZeroTime
        /*
        // 根据type是按天还是按星期，设置
        when (planType) {
            TaskPlan.TYPE_WEEK -> {
                var todayOfWeek = Calendar.getInstance().also { it.time = Date() }.get(Calendar.DAY_OF_WEEK)
                this.startZeroTime = todayZeroTime + (dayOfWeek-todayOfWeek)*86400
                this.oneCycleDays = 7
            }
            else -> this.startZeroTime = todayZeroTime
        }
         */
    }
    constructor(id: Int, name: String, mtime: Int, drawableId: Int): this(name, mtime, drawableId) { this.id = id }
    constructor(id: Int, name: String, mtime: Int, drawableId: Int, note: String): this(name, mtime, drawableId, note) { this.id = id }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeInt(id!!)
            writeString(name)
            writeInt(drawableId)
            writeInt(totalMtime)
            writeInt(totalMtimeDone)
            writeInt(addTime)
            writeString(note)

            writeInt(planType)
            writeInt(cycleWeekBits)
            writeInt(cycleDays)
            writeInt(startZeroTime)
            writeInt(cycleMtime)
            writeInt(cycleMtimeDone)
        }
    }

    fun setTaskByPlanInfo(planInfoOnly: TaskPlan.PlanInfoOnly) {
        planInfoOnly.let {
            // no tid
            planType = it.type
            cycleDays = it.cycleDays
            cycleMtime = it.cycleMtime
            cycleMtimeDone = it.cycleMtimeDone
            cycleWeekBits = it.getWeekBitsFromList()
        }
    }

    override fun equals(other: Any?): Boolean {
        (other as Task)?.let {
            return (
                    (id == it.id) && (name == it.name) && (drawableId == it.drawableId) && (totalMtime == it.totalMtime)
                            && (totalMtimeDone == it.totalMtimeDone) &&(addTime == it.addTime) && (note == it.note)
                            && (planType == it.planType) && (cycleWeekBits == it.cycleWeekBits) && (startZeroTime == it.startZeroTime)
                            && (cycleDays == it.cycleDays) && (cycleMtime == it.cycleMtime) && (cycleMtimeDone == it.cycleMtimeDone)
                    )
        }
        return false
    }

    fun copy(inTask: Task) {
        inTask.let {
            id = it.id
            name = it.name
            drawableId = it.drawableId
            totalMtime = it.totalMtime
            totalMtimeDone = it.totalMtimeDone
            addTime = it.addTime
            note = it.note

            planType = it.planType
            cycleWeekBits = it.cycleWeekBits
            startZeroTime = it.startZeroTime
            cycleDays = it.cycleDays
            cycleMtime = it.cycleMtime
            cycleMtimeDone = it.cycleMtimeDone
        }
    }

    override fun describeContents(): Int { return 0 }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

}

