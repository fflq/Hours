package com.example.hours.home.task


import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.dialog.SelectIconDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_task_add.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 */
class TaskEditFragment : TaskAddFragment() {
    lateinit var argTask: Task

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.fragment_task_add, container, false)

        argTask = arguments?.getParcelable("task")!!
        rootView.apply {
            etTaskName.text = SpannableStringBuilder(argTask.name)
            etTaskTime.text = SpannableStringBuilder((argTask.totalMtime / 60).toString())
            etIntervalDay.text = SpannableStringBuilder(argTask.cycleDays.toString())
            etTimePlan.text = SpannableStringBuilder(argTask.oneCycleMtime.toString())
            etNote.text = SpannableStringBuilder(argTask.note)
            ivIconSelected.apply {
                tag = argTask.drawableId
                image = resources.getDrawable(argTask.drawableId, null)
            }
        }
        rootView.clt141.onClick {
            SelectIconDialogFragment(rootView.ivIconSelected).show(parentFragmentManager, "dialog")
        }

        return rootView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun handleTask(input: Task) {
        activity?.bottom_nav_view?.visibility = View.GONE
        argTask.apply {
            this.name = input.name
            this.drawableId = input.drawableId
            this.totalMtime = input.totalMtime
            this.oneCycleMtime = input.oneCycleMtime
            this.cycleDays = input.cycleDays
            this.note = input.note
        }
        taskViewModel?.update(argTask)
    }

}
