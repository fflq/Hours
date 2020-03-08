package com.example.hours.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.hours.Box

class PomodoroViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val key = "time"
    var boxTime = Box<Int>(1).also {
        if (!savedStateHandle.contains(key))
            savedStateHandle.set(key, 1)
        it.v = savedStateHandle.get(key) ?: 1
    }
    /*
    var boxTime = MutableLiveData<Box<Int>>().also {
        if (!savedStateHandle.contains(key))
            savedStateHandle.set(key, 1)
        (it.value as Box<Int>).v = savedStateHandle.get<Int>(key) ?: 1
    }
    */
}