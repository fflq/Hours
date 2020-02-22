package com.example.hours.home.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hours.R

class TaskViewModel(application: Application): AndroidViewModel(application) {
    var taskRepository: TaskRepository = TaskRepository(application)
    var liveAllTasks: LiveData<List<Task>>
    var liveTodayTasks: LiveData<List<Task>>

    var sharedPreferences: SharedPreferences
    var sharedPreferencesEditor: SharedPreferences.Editor
    var liveSort = MutableLiveData<String>()
    var sort = ""

    init {
        sharedPreferences = application.getSharedPreferences(application.getString(R.string.pref_file), Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        liveSort.value = sharedPreferences.getString(application.getString(R.string.sort), "")!!

        this.sort = liveSort.value as String
        this.liveAllTasks = selectAll(sort)
        this.liveTodayTasks = selectToday(sort)
    }

    fun notifySortChanged() {
        this.sort = liveSort.value as String
        this.liveAllTasks = selectAll(sort)
        this.liveTodayTasks = selectToday(sort)
    }

    fun selectAll(sort: String=this.sort): LiveData<List<Task>> = taskRepository.selectAll(sort)

    fun selectAllByNameLike(name: String): LiveData<List<Task>> {
        // search name is empty, resotre sort order, optimize future
        if (name.isEmpty()) return selectAll()
        return taskRepository.taskDao.selectAllByNameLike("%$name%")
    }

    fun selectToday(sort: String=this.sort) = taskRepository.selectToday(sort)

    fun selectTodayByNameLike(name: String): LiveData<List<Task>> {
        if (name.isEmpty()) return selectToday()
        return taskRepository.taskDao.selectTodayByNameLike("%$name%")
    }


    fun insert(vararg task: Task) = taskRepository.insert(*task)

    fun update(vararg task: Task) = taskRepository.update(*task)

    fun delete(vararg task: Task) = taskRepository.delete(*task)
}