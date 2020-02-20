package com.example.hours

import java.util.*


//获取date对象
var data: Date = Date()
//获取日历对象
var calendar: Calendar = Calendar.getInstance()

//获取今天是星期几
fun Date.getToday_Englishname(): String {
    var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    calendar.time = data
    var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
    if (index < 0) {
        index = 0
    }
    return list[index]
}

interface BeforeBackListener { fun beforeBack() }

class Box<T>(var v: T)

