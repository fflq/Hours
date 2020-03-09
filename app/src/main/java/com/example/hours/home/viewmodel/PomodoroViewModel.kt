package com.example.hours.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.hours.home.view.PomodoroView

class PomodoroViewModel(var savedStateHandle: SavedStateHandle) : ViewModel() {

    private val keyTime = "time"
    var time: Long
    get() = getterSavedStateHandle(keyTime, 0L)
    set(value) = savedStateHandle.set (keyTime, value)

    private val keyCountDownTime = "count_down_time"
    var countDownTime: Long
    get() = getterSavedStateHandle(keyCountDownTime, 0L)
    set(value) = savedStateHandle.set(keyCountDownTime, value)

    val keyStatus = "status"
    var status: PomodoroView.STATUS
    get() = getterSavedStateHandle(keyStatus, PomodoroView.STATUS.STOPED)
    set(value) = savedStateHandle.set(keyStatus, value)

    private fun <T> getterSavedStateHandle(key: String, def: T): T {
        if (!savedStateHandle.contains(key))
            savedStateHandle.set (key, def)
        return savedStateHandle.get(key)!!
    }

    /*
    var boxTime = MutableLiveData<Box<Int>>().also {
        if (!savedStateHandle.contains(key))
            savedStateHandle.set(key, 1)
        (it.value as Box<Int>).v = savedStateHandle.get<Int>(key) ?: 1
    }
    */
}