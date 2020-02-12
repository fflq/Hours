package com.example.hours.home.task


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.fragment_task_add_time.*
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
        return inflater.inflate(R.layout.fragment_task_add_time, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        argTask = arguments?.getParcelable<Task>("task")!!
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_add_menu, menu)
    }


    private fun save() {
        if (etTimeAdd.text.isNotEmpty()) {
            val mtimeAdd = etTimeAdd.text.toString().toInt()
            argTask.totalMtimeHasDone += mtimeAdd
            argTask.oneCycleMtimeHasDone += mtimeAdd
            taskViewModel?.update(argTask)

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etTimeAdd.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

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
