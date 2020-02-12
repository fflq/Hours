package com.example.hours.home.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.hours.R
import com.example.hours.home.data.Task

class TaskFragment : HomeBaseFragment() {

    override fun getToDetailAction() = R.id.action_taskFragment_to_taskDetailFragment
    override fun getToAddTimeAction() = R.id.action_taskFragment_to_taskAddTimeFragment
    override fun getMenuLayout(): Int = R.menu.task_menu
    override fun selectByNameLike(name: String): LiveData<List<Task>>? = this.taskViewModel?.selectAllByNameLike(name)


    override fun initVarOnce() {
        super.initVarOnce()
        this.liveTasks = this.liveTasks?: this.taskViewModel!!.liveAllTasks
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.task_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_menu_add -> navController?.navigate(R.id.action_taskFragment_to_taskAddFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}
