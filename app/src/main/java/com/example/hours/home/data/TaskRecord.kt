package com.example.hours.home.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task_Record")
class TaskRecord(): Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var tid: Int = 0
    var addTime: Int = (System.currentTimeMillis()/1000).toInt()
    var mtimeDone: Int = 0
    var note: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        tid = parcel.readInt()
        addTime = parcel.readInt()
        mtimeDone = parcel.readInt()
        note = parcel.readString().toString()
    }

    constructor(tid: Int, mtimeDone: Int, note: String): this() {
        this.tid = tid
        this.mtimeDone = mtimeDone
        this.note = note
    }


    override fun equals(other: Any?): Boolean {
        (other as TaskRecord)?.let {
            return ((id == it.id) && (tid == it.tid) && (addTime == it.addTime) && (mtimeDone == it.mtimeDone)
                    && (note == it.note)
                    )
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeInt(tid)
        parcel.writeInt(addTime)
        parcel.writeInt(mtimeDone)
        parcel.writeString(note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskRecord> {
        override fun createFromParcel(parcel: Parcel): TaskRecord {
            return TaskRecord(parcel)
        }

        override fun newArray(size: Int): Array<TaskRecord?> {
            return arrayOfNulls(size)
        }
    }
}