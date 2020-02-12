package com.example.hours.home.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    var taskRepository: TaskRepository = TaskRepository(application)
    var liveAllTasks: LiveData<List<Task>>
    var liveTodayTasks: LiveData<List<Task>>

    init {
        liveAllTasks = selectAll(true)!!
        liveTodayTasks = taskRepository.taskDao.selectToday()!!
    }


    fun selectAll(isnew :Boolean=false): LiveData<List<Task>> = when(isnew) {
        true -> taskRepository.selectAll()
        else -> liveAllTasks
    }

    fun selectAllByNameLike(name: String): LiveData<List<Task>> = taskRepository.selectAllByNameLike(name)

    fun selectToday() = taskRepository.taskDao.selectToday()

    fun selectTodayByNameLike(name: String): LiveData<List<Task>> = taskRepository.taskDao.selectTodayByNameLike(name)

    fun insert(vararg word: Task) = taskRepository.insert(*word)

    fun update(vararg word: Task) = taskRepository.update(*word)

    fun delete(vararg word: Task) = taskRepository.delete(*word)
}