package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {
    companion object {
        const val TABLE = "task"
        const val TODAY = "1=1"
        const val ORDER_ID = "order by id desc"
    }

    @Query("select * from $TABLE ")
    fun selectAll(): LiveData<List<Task>>

    @Query("select * from $TABLE order by name asc")
    fun selectAllOrderByNameAsc(): LiveData<List<Task>>
    @Query("select * from $TABLE order by name desc")
    fun selectAllOrderByNameDesc(): LiveData<List<Task>>
    @Query("select * from $TABLE order by addTime asc")
    fun selectAllOrderByAddtimeAsc(): LiveData<List<Task>>
    @Query("select * from $TABLE order by addTime desc")
    fun selectAllOrderByAddtimeDesc(): LiveData<List<Task>>

    @Query("select * from $TABLE where name like :name")
    fun selectAllByNameLike(name: String): LiveData<List<Task>>


    @Query("select * from $TABLE where $TODAY")
    fun selectToday(): LiveData<List<Task>>

    @Query("select * from $TABLE where $TODAY order by name asc")
    fun selectTodayOrderByNameAsc(): LiveData<List<Task>>
    @Query("select * from $TABLE where $TODAY order by name desc")
    fun selectTodayOrderByNameDesc(): LiveData<List<Task>>
    @Query("select * from $TABLE where $TODAY order by addTime asc")
    fun selectTodayOrderByAddtimeAsc(): LiveData<List<Task>>
    @Query("select * from $TABLE where $TODAY order by addTime desc")
    fun selectTodayOrderByAddtimeDesc(): LiveData<List<Task>>

    @Query("select * from $TABLE where $TODAY and name like :name")
    fun selectTodayByNameLike(name: String): LiveData<List<Task>>


    @Update
    fun update(vararg word: Task)

    @Delete
    fun delete(vararg word: Task)

    @Insert
    fun insert(vararg word: Task)
    @Insert
    fun insertOne(word: Task): Long
}