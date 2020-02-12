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

    fun selectAll(): LiveData<List<Task>> = taskDao.selectAll()

    fun selectAllByNameLike(name: String): LiveData<List<Task>> = taskDao.selectAllByNameLike("%$name%")

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
}