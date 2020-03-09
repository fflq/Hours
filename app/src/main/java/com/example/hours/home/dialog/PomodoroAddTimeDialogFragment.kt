package com.example.hours.home.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.hours.Box
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskViewModel
import kotlinx.android.synthetic.main.dialog_pomodoro_add_time.view.*

class PomodoroAddTimeDialogFragment(var boxMtime: Box<Int>): DialogFragment() {

    lateinit var taskViewModel: TaskViewModel
    //var boxMtime = Box(0)
    var taskSelected: Task? = null
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.dialog_pomodoro_add_time, container)

        taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        /*
        root.tvSelectTask.onClick {
            SelectTaskDialogFragment(taskSelected).let {
                it.onSubmitListener = View.OnClickListener {
                }
                it.show(parentFragmentManager, "dialog")
            }
        }
        */
        taskViewModel.liveAllTasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
            var taskNames = ArrayList<String>()
            taskNames.add ("select task")
            list.forEach { taskNames.add(it.name) }

            // 要动态的设置adapter
            root.spinner_task.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, taskNames).also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
        })

        root.spinner_task.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                taskSelected = taskViewModel.liveAllTasks.value?.getOrNull(position-1)    // 因为第0项是提示信息
                Log.d("aw", taskSelected?.name.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { taskSelected = null }
        }

        return root
    }
}