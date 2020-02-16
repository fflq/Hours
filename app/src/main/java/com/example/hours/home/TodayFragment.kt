package com.example.hours.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.task.HomeBaseFragment
import com.example.hours.home.task.TaskAdapter

class TodayFragment : HomeBaseFragment() {

    override val toDetailAction get() = R.id.action_todayFragment_to_taskDetailFragment
    override val toAddTimeAction get() = R.id.action_todayFragment_to_taskAddTimeFragment
    override val menuLayout: Int get() = R.menu.today_menu
    override val taskAdapterType: TaskAdapter.TYPE get() = TaskAdapter.TYPE.TODAY
    override val liveTasks: LiveData<List<Task>>? get() = taskViewModel?.liveTodayTasks

    override fun selectByNameLike(name: String): LiveData<List<Task>>? = this.taskViewModel?.selectTodayByNameLike(name)


    override fun initVarOnce() {
        super.initVarOnce()
    }


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
