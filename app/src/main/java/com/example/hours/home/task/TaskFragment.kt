package com.example.hours.home.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.hours.R
import com.example.hours.databinding.FragmentTaskBinding
import com.example.hours.home.adapter.TaskAdapter
import com.example.hours.home.base.HomeBaseFragment
import com.example.hours.home.data.Task
import org.jetbrains.anko.sdk27.coroutines.onClick

class TaskFragment : HomeBaseFragment() {

    lateinit var binding: FragmentTaskBinding

    override val toDetailAction get() = R.id.action_taskFragment_to_taskDetailFragment
    override val toAddTimeAction get() = R.id.action_taskFragment_to_taskAddTimeFragment
    override val menuLayout: Int get() = R.menu.task_menu
    override val taskAdapterType: TaskAdapter.TYPE get() = TaskAdapter.TYPE.TASK
    override val liveTasks: LiveData<List<Task>>? get() = taskViewModel?.liveAllTasks

    override fun selectByNameLike(name: String): LiveData<List<Task>>? = this.taskViewModel?.selectAllByNameLike(name)

    override fun onListChange(isNullOrEmpty: Boolean) {
        Log.d ("obc", "a")
        binding.hasTasks = !isNullOrEmpty
    }

    override fun initVarOnce() {
        super.initVarOnce()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.ivAddTask.onClick {  navController?.navigate(R.id.action_taskFragment_to_taskAddFragment) }
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> navController?.navigate(R.id.action_taskFragment_to_taskAddFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}
