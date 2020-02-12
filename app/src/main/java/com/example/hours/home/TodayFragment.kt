package com.example.hours.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.task.HomeBaseFragment

class TodayFragment : HomeBaseFragment() {

    override fun getToDetailAction() = R.id.action_todayFragment_to_taskDetailFragment
    override fun getToAddTimeAction() = R.id.action_todayFragment_to_taskAddTimeFragment
    override fun getMenuLayout(): Int = R.menu.today_menu
    override fun selectByNameLike(name: String): LiveData<List<Task>>? = this.taskViewModel?.selectTodayByNameLike(name)


    override fun initVarOnce() {
        super.initVarOnce()
        this.liveTasks = this.liveTasks?: this.taskViewModel!!.liveTodayTasks
    }


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
