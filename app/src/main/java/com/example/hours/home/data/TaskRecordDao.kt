package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskRecordDao {
    companion object {
        const val TABLE = "task_record"

        // 现在ts
        const val NOW_TS = "strftime('%s','now')"
        // 今天0点ts
        const val ZERO_TS = "$NOW_TS - ($NOW_TS + 3600 * 8) % 86400"  // CST+8
    }


    @Insert
    fun insert(vararg word: TaskRecord)

    @Query("select * from $TABLE order by id desc")
    fun selectAll(): LiveData<List<TaskRecord>>

    @Query("select * from $TABLE where tid=:tid order by addTime desc")
    fun select(tid: Int): LiveData<List<TaskRecord>>

    @Query("select sum(mtimeDone) as sum, (addTime-(addTime+3600*8)%86400) as zeroTime from $TABLE where tid=:tid group by zeroTime order by zeroTime desc limit 7")
    fun selectStats(tid: Int): LiveData<List<TaskRecord.Stats>>
}