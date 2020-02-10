package com.example.hours.home.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    var taskRepository: TaskRepository = TaskRepository(application)
    var liveAllTasks: LiveData<List<Task>>
    var liveSelectTasks: LiveData<List<Task>>

    init {
        liveAllTasks = selectAll(true)!!
        liveSelectTasks = selectByNameLike("a")!!
    }


    fun selectAll(isnew :Boolean=false): LiveData<List<Task>> = when(isnew) {
        true -> taskRepository.selectAll()
        else -> liveAllTasks
    }

    fun selectByNameLike(name: String): LiveData<List<Task>> = taskRepository.selectByNameLike(name)

    fun insert(vararg word: Task) = taskRepository.insert(*word)

    fun update(vararg word: Task) = taskRepository.update(*word)
}