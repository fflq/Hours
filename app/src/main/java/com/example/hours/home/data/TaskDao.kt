package com.example.hours.home.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {
    companion object {
        const val TABLE = "task"

        // 获取星期几的编号1-7
        const val WEEK = "(1+strftime('%w', 'now'))"
        // plan尚未完成
        const val NOT_DONE = "( (totalMtimeDone<totalMtime) and (cycleMtimeDone<cycleMtime) )"
        // 今天有计划
        const val HAS_PLAN = "( (planType=0 and cycleDays>0) or (planType=1 and (1<<$WEEK)&cycleWeekBits != 0) )"
        // 今天
        const val TODAY = "$NOT_DONE" + "and $HAS_PLAN"


        // 现在ts
        const val NOW_TS = "strftime('%s','now')"
        // 今天0点ts
        const val ZERO_TS = "$NOW_TS - ($NOW_TS + 3600 * 8) % 86400"  // CST+8
        // 需要更新mtimeDone为0的条件
        const val ZERO_MTIME_DONE = "(planType = 0)" + "or ( planType=1 and ($NOW_TS-startZeroTime)<(cycleDays*86400) )"
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

    @Query("update task set cycleMtimeDone=0, startZeroTime=$ZERO_TS where $ZERO_MTIME_DONE")
    fun updateCycleMtimeDone()

    @Update
    fun update(vararg word: Task)

    @Delete
    fun delete(vararg word: Task)

    @Insert
    fun insert(vararg word: Task)
    @Insert
    fun insertOne(word: Task): Long
}

