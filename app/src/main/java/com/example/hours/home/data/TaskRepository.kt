package com.example.hours.home.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TaskRepository(application: Application) {
    var taskDao: TaskDao
    var taskRecordDao: TaskRecordDao
    init {
        var taskDatabase = TaskDatabase.getTaskDatabase(application.applicationContext)!!
        taskDao = taskDatabase.getTaskDao()!!
        taskRecordDao = taskDatabase.getTaskRecordDao()!!
    }


    fun selectAll(sort: String): LiveData<List<Task>> {
        return when (sort) {
            "name asc" -> taskDao.selectAllOrderByNameAsc()
            "name desc" -> taskDao.selectAllOrderByNameDesc()
            "addTime asc" -> taskDao.selectAllOrderByAddtimeAsc()
            else -> taskDao.selectAllOrderByAddtimeDesc()
        }
    }

    fun selectToday(sort: String): LiveData<List<Task>> {
        return when (sort) {
            "name asc" -> taskDao.selectTodayOrderByNameAsc()
            "name desc" -> taskDao.selectTodayOrderByNameDesc()
            "addTime asc" -> taskDao.selectTodayOrderByAddtimeAsc()
            else -> taskDao.selectTodayOrderByAddtimeDesc()
        }
    }

    //fun selectAllByNameLike(name: String): LiveData<List<Task>> = taskDao.selectAllByNameLike(name)

    fun insert(vararg task: Task) = InsertAsyncTask(taskDao).execute(*task)

    fun update(vararg task: Task) = UpdateAsyncTask(taskDao).execute(*task)

    fun delete(vararg task: Task) = DeleteAsyncTask(taskDao).execute(*task)

    class InsertAsyncTask(var taskDao: TaskDao): AsyncTask<Task, Unit, Unit>() {
        override fun doInBackground(vararg params: Task) = taskDao.insert(*params)
    }

    class UpdateAsyncTask(var taskDao: TaskDao): AsyncTask<Task, Unit, Unit>() {
        override fun doInBackground(vararg params: Task) = taskDao.update(*params)
    }

    class DeleteAsyncTask(var taskDao: TaskDao): AsyncTask<Task, Unit, Unit>() {
        override fun doInBackground(vararg params: Task) = taskDao.delete(*params)
    }


    fun selectRecordsByTid(tid: Int): LiveData<List<TaskRecord>> = taskRecordDao.select(tid)

    fun insertRecords(vararg taskRecord: TaskRecord) = InsertTaskRecordAsyncTask(taskRecordDao).execute(*taskRecord)

    class InsertTaskRecordAsyncTask(var taskRecordDao: TaskRecordDao): AsyncTask<TaskRecord, Unit, Unit>() {
        override fun doInBackground(vararg params: TaskRecord) = taskRecordDao.insert(*params)
    }
}
