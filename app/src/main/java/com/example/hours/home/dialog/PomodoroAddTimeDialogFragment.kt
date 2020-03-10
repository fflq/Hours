package com.example.hours.home.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskRecord
import com.example.hours.home.data.TaskViewModel
import kotlinx.android.synthetic.main.dialog_pomodoro_add_time.view.*
import kotlinx.android.synthetic.main.fragment_task_add_time.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class PomodoroAddTimeDialogFragment(var mTime: Int): DialogFragment() {

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

        root.btn_submit.onClick {
            if (taskSelected is Task) {
                taskSelected?.let { taskAddTime(it, mTime) }
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow( etNote.windowToken, InputMethodManager.HIDE_NOT_ALWAYS )
                toast ("add $mTime minutes")
                this@PomodoroAddTimeDialogFragment.dismiss()
            }
            else    toast("please select task")
        }

        return root
    }


    private fun taskAddTime(argTask: Task, mTimeAdd: Int) {
        argTask.totalMtimeDone += mTimeAdd
        argTask.cycleMtimeDone += mTimeAdd
        taskViewModel?.update(argTask)

        taskViewModel?.taskRepository?.insertRecords(TaskRecord(argTask.id!!, mTimeAdd, etNote.text.toString()))
    }

}