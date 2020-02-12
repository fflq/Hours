package com.example.hours.home.task


import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_task_add.*

/**
 * A simple [Fragment] subclass.
 */
class TaskEditFragment : TaskAddFragment() {
    lateinit var argTask: Task

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.bottom_nav_view?.visibility = View.GONE
        super.onActivityCreated(savedInstanceState)

        activity?.bottom_nav_view?.visibility = View.GONE
        argTask = arguments?.getParcelable("task")!!
        if (argTask is Task) {
            etTaskName.text = SpannableStringBuilder(argTask.name)
            etTaskTime.text = SpannableStringBuilder((argTask.totalMtime / 60).toString())
            etIntervalDay.text = SpannableStringBuilder(argTask.cycleDays.toString())
            etTimePlan.text = SpannableStringBuilder(argTask.oneCycleMtime.toString())
            this.myRadioTableLayoutManager.selectByDrawableId(argTask.drawableId)
        }
    }

    override fun handleTask(input: Task) {
        activity?.bottom_nav_view?.visibility = View.GONE
        argTask.apply {
            this.name = input.name
            this.drawableId = input.drawableId
            this.totalMtime = input.totalMtime
            this.oneCycleMtime = input.oneCycleMtime
            this.cycleDays = input.cycleDays
        }
        taskViewModel?.update(argTask)
    }

}
