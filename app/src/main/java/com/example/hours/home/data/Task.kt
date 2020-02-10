package com.example.hours.home.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
class Task(
    var name: String?,
    var mtime: Int = 0,
    var drawableId: Int = 0) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var addTime: Long = System.currentTimeMillis()
    var mtimeDone: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        addTime = parcel.readLong()
        mtimeDone = parcel.readInt()
    }

    constructor(id: Int, name: String, mtime: Int, drawableId: Int): this(name, mtime, drawableId) {
        this.id = id
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeInt(id!!)
            writeLong(addTime)
            writeInt(mtimeDone)
            writeInt(mtime)
            writeInt(drawableId)
            writeString(name)
        }
    }


    override fun equals(other: Any?): Boolean {
        (other as Task)?.let {
            return (
                    (id == it.id) && (name == it.name) && (drawableId == it.drawableId) && (mtime == it.mtime)
                            &&(addTime == it.addTime) && (mtimeDone == it.mtimeDone)
                    )
        }
        return false
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}

