package com.example.hours.home.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Task::class, TaskRecord::class, TaskPlan::class), version = 18, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    companion object {
        var instance: TaskDatabase? = null
        val DATABASE_NAME = "Task_Database"

        fun getTaskDatabase(context: Context): TaskDatabase {
            synchronized(TaskDatabase::class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }

    abstract fun getTaskDao(): TaskDao

    abstract fun getTaskRecordDao(): TaskRecordDao

}
