package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskRecordDao {
    companion object {
        const val TABLE = "task_record"
    }


    @Insert
    fun insert(vararg word: TaskRecord)

    @Query("select * from ${TaskRecordDao.TABLE} order by id desc")
    fun selectAll(): LiveData<List<TaskRecord>>

    @Query("select * from ${TaskRecordDao.TABLE} where tid=:tid order by addTime desc")
    fun select(tid: Int): LiveData<List<TaskRecord>>

}