package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    companion object {
        const val TABLE = "task"
        const val TODAY = "oneCycleMtime!=0 and oneCycleMtimeHasDone<oneCycleMtime"
    }

    @Insert
    fun insert(vararg word: Task)

    @Query("select * from $TABLE")
    fun selectAll(): LiveData<List<Task>>

    @Query("select * from $TABLE where name like :name")
    fun selectAllByNameLike(name: String): LiveData<List<Task>>

    @Query("select * from $TABLE where $TODAY")
    fun selectToday(): LiveData<List<Task>>

    @Query("select * from $TABLE where $TODAY and name like :name")
    fun selectTodayByNameLike(name: String): LiveData<List<Task>>

    @Update
    fun update(vararg word: Task)

    @Query("delete from $TABLE")
    fun deleteAll()

    @Delete
    fun delete(vararg word: Task)
}