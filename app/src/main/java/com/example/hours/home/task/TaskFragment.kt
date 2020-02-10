package com.example.hours.home.task

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.task_fragment.*

class TaskFragment : HomeBaseFragment() {
    private var taskAdapter: TaskAdapter? = null


    override fun initVarOnce() {
        super.initVarOnce()

        this.taskAdapter = this.taskAdapter?: TaskAdapter(context).also {
            it.onItemClickListener = object: TaskAdapter.OnTaskClickListener {
                override fun onClick(v: View?, task: Task) {
                    var bundle = Bundle().apply { putParcelable("task", task) }
                    navController?.navigate(R.id.action_taskFragment_to_taskDetailFragment, bundle)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val rootView = inflater.inflate(R.layout.task_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        taskViewModel?.liveAllTasks?.observe(viewLifecycleOwner, Observer<List<Task>> {
            this.taskAdapter?.submitList(it)
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_menu_add -> navController?.navigate(R.id.action_taskFragment_to_taskAddFragment)

        }
        return super.onOptionsItemSelected(item)
    }

}
