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
    var totalMtimeHasDone: Int = 0
    var addTime: Int = (System.currentTimeMillis()/1000).toInt()
    // for today select, can put to new table
    var startDate: Int = addTime - (addTime+3600*8)%86400
    var cycleDays: Int = 0
    var oneCycleMtime: Int = 0
    var oneCycleMtimeHasDone: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()!!
        drawableId = parcel.readInt()
        totalMtime = parcel.readInt()
        totalMtimeHasDone = parcel.readInt()
        addTime = parcel.readInt()
        startDate = parcel.readInt()
        cycleDays = parcel.readInt()
        oneCycleMtime = parcel.readInt()
        oneCycleMtimeHasDone = parcel.readInt()
    }


    constructor(name: String, mtime: Int, drawableId: Int): this() {
        this.name = name
        this.totalMtime = mtime
        this.drawableId = drawableId
    }
    constructor(id: Int, name: String, mtime: Int, drawableId: Int): this(name, mtime, drawableId) { this.id = id }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeInt(id!!)
            writeString(name)
            writeInt(drawableId)
            writeInt(totalMtime)
            writeInt(totalMtimeHasDone)
            writeInt(addTime)
            writeInt(startDate)
            writeInt(cycleDays)
            writeInt(oneCycleMtime)
            writeInt(oneCycleMtimeHasDone)
        }
    }


    override fun equals(other: Any?): Boolean {
        (other as Task)?.let {
            return (
                    (id == it.id) && (name == it.name) && (drawableId == it.drawableId) && (totalMtime == it.totalMtime) && (totalMtimeHasDone == it.totalMtimeHasDone)
                            &&(addTime == it.addTime) && (startDate == it.startDate) && (cycleDays == it.cycleDays)
                            &&(oneCycleMtime == it.oneCycleMtime) &&(oneCycleMtimeHasDone == it.oneCycleMtimeHasDone)
                    )
        }
        return false
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

