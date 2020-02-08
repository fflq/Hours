package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    companion object {
        const val TABLE = "task"
    }

    @Insert
    fun insert(vararg word: Task)

    @Query("select * from $TABLE order by id desc")
    fun selectAll(): LiveData<List<Task>>

    @Query("select * from $TABLE where name like :pattern order by id desc")
    fun select(pattern: String): LiveData<List<Task>>

    @Update
    fun update(vararg word: Task)

    @Query("delete from $TABLE")
    fun deleteAll()

    @Delete
    fun delete(vararg word: Task)
}