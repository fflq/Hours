package com.example.hours.home.task


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskRecord
import kotlinx.android.synthetic.main.fragment_task.*

/**
 * A simple [Fragment] subclass.
 */
class TaskRecordsFragment : NotHomeBaseFragment() {

    var taskRecordAdapter: TaskRecordAdapter? = null
    var liveTaskRecords: LiveData<List<TaskRecord>>? = null


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_records, container, false)
    }


    override fun initVarOnce() {
        super.initVarOnce()

        this.taskRecordAdapter = this.taskRecordAdapter?: TaskRecordAdapter().also {
            it.onItemClickListener = object: TaskRecordAdapter.OnTaskRecordClickListener {
                override fun onClick(v: View?, taskRecord: TaskRecord) {
                    var bundle = Bundle().apply { putParcelable("taskRecord", taskRecord) }
                    //navController?.navigate(getToDetailAction(), bundle)
                }
            }
        }

        val task = arguments?.getParcelable<Task>("task")!!
        this.liveTaskRecords = this.liveTaskRecords?: taskViewModel?.taskRepository?.selectRecordsByTid(task.id!!)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        srlSwipeRefresh.setOnRefreshListener {
            srlSwipeRefresh.isRefreshing = true
            Handler().postDelayed({ srlSwipeRefresh.isRefreshing = false }, 1000)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskRecordAdapter
        }

        if (liveTaskRecords!!.hasObservers()) liveTaskRecords?.removeObservers(viewLifecycleOwner)
        liveTaskRecords?.observe(viewLifecycleOwner, Observer<List<TaskRecord>> {
            this.taskRecordAdapter?.submitList(it)
        })
    }
}
