package com.example.hours.home.task


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskRecord
import com.example.hours.home.dialog.SelectTimeDialogFragment
import kotlinx.android.synthetic.main.fragment_task_add_time.*
import kotlinx.android.synthetic.main.fragment_task_add_time.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class TaskAddTimeFragment : NotHomeBaseFragment() {
    lateinit var argTask: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_task_add_time, container, false)
        rootView.tvSelectTime.apply {
            onClick { SelectTimeDialogFragment(rootView.tvSelectTime).show(parentFragmentManager, "dialog") }
            this.tag = 0
        }
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        argTask = arguments?.getParcelable<Task>("task")!!
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_save_menu, menu)
    }


    private fun save() {
        if (tvSelectTime.text.isNotEmpty()) {
            val mtimeAdd = tvSelectTime.tag as Int
            argTask.totalMtimeDone += mtimeAdd
            argTask.cycleMtimeDone += mtimeAdd
            taskViewModel?.update(argTask)

            taskViewModel?.taskRepository?.insertRecords(TaskRecord(argTask.id!!, mtimeAdd, etNote.text.toString()))

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(tvSelectTime.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            navController?.navigateUp()
        }
        else    toast("please input")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_menu_save -> save ()

        }
        return super.onOptionsItemSelected(item)
    }

}
