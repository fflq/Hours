package com.example.hours.home.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    var mTaskRepository: TaskRepository = TaskRepository(application)
    var mLiveWords: LiveData<List<Task>>
    init {
        mLiveWords = mTaskRepository.selectAll()!!
    }


    fun select(pattern: String): LiveData<List<Task>> {
        return mTaskRepository.select(pattern)
    }

    fun selectAll(isnew :Boolean=false): LiveData<List<Task>> = when(isnew) {
        true -> mTaskRepository.selectAll()
        else -> mLiveWords
    }

    fun insert(vararg word: Task) = mTaskRepository.insert(*word)
}