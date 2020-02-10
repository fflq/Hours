package com.example.hours.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.task.HomeBaseFragment
import com.example.hours.home.task.TaskAdapter
import kotlinx.android.synthetic.main.task_fragment.*

class TodayFragment : HomeBaseFragment() {
    private var taskAdapter: TaskAdapter? = null


    override fun initVarOnce() {
        super.initVarOnce()

        this.taskAdapter = this.taskAdapter?: TaskAdapter(context).also {
            it.onItemClickListener = object: TaskAdapter.OnTaskClickListener {
                override fun onClick(v: View?, task: Task) {
                    var bundle = Bundle().apply { putParcelable("task", task) }
                    navController?.navigate(R.id.action_todayFragment_to_taskDetailFragment, bundle)
                }
            }
            it.onItemAddTimeClickListener = object : TaskAdapter.OnTaskClickListener {
                override fun onClick(v: View?, task: Task) {
                    task.mtimeDone += 30
                    taskViewModel?.update(task)
                }
            }
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        taskViewModel?.liveSelectTasks?.observe(viewLifecycleOwner, Observer<List<Task>> {
            this.taskAdapter?.submitList(it)
        })
    }


}
