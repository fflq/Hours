package com.example.hours.home.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TaskRepository(application: Application) {
    var taskDao: TaskDao
    init {
        var taskDatabase = TaskDatabase.getTaskDatabase(application.applicationContext)!!
        taskDao = taskDatabase.getTaskDao()!!
    }

    fun select(pattern: String): LiveData<List<Task>> = taskDao.select("%$pattern%")

    fun selectAll(): LiveData<List<Task>> = taskDao.selectAll()

    fun insert(vararg task: Task) = InsertAsyncTask(taskDao).execute(*task)


    class InsertAsyncTask(var taskDao: TaskDao): AsyncTask<Task, Unit, Unit>() {
        override fun doInBackground(vararg params: Task) = taskDao.insert(*params)
    }
}