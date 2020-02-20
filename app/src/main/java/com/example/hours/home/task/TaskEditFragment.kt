package com.example.hours.home.task


import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hours.BeforeBackListener
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskPlan
import com.example.hours.home.data.TaskPlan.Companion.TYPE_WEEK
import com.example.hours.home.dialog.SelectIconDialogFragment
import com.example.hours.home.dialog.SelectPlanDialogFragment
import kotlinx.android.synthetic.main.fragment_task_add.*
import kotlinx.android.synthetic.main.fragment_task_add.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 */
class TaskEditFragment : TaskAddFragment() {
    lateinit var argTask: Task
    private var planInfoOnly = TaskPlan.PlanInfoOnly()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.fragment_task_add, container, false)

        argTask = arguments?.getParcelable("task")!!
        rootView.apply {
            etTaskName.text = SpannableStringBuilder(argTask.name)
            etTaskTime.text = SpannableStringBuilder((argTask.totalMtime / 60).toString())
            etNote.text = SpannableStringBuilder(argTask.note)
            ivIconSelected.image = resources.getDrawable(argTask.drawableId, null)
            boxSelectDrawableId.v = argTask.drawableId
        }

        // 选择图标的dialogfragment
        rootView.clt141.onClick {
            SelectIconDialogFragment(boxSelectDrawableId).also {
                it.beforeBackListener = object : BeforeBackListener {
                    override fun beforeBack() {
                        rootView.ivIconSelected.image = resources.getDrawable(boxSelectDrawableId.v, null)
                    }
                }
                it.show(parentFragmentManager, "dialog")
            }
        }


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // 准备计划的数据，db操作在onCreateView中会空指针
        var taskPlanList: List<TaskPlan> = taskViewModel?.taskRepository?.selectPlansByTid(argTask.id!!)!!
        if (taskPlanList.isNotEmpty()) {
            taskPlanList[0]?.let {
                planInfoOnly.type = it.type
                planInfoOnly.oneCycleMtime = it.oneCycleMtime
                planInfoOnly.oneCycleDays = it.oneCycleDays
            }
        }
        if (planInfoOnly.type == TYPE_WEEK) taskPlanList.forEach { planInfoOnly.dayOfWeekList.add(it.dayOfWeek) }
        tvPlan.text = planInfoOnly.toString()
        // 选择计划的dialogfragment
        tvPlan.onClick {
            SelectPlanDialogFragment(planInfoOnly).also {
                it.beforeBackListener = object : BeforeBackListener {
                    override fun beforeBack() { tvPlan.text = planInfoOnly.toString() }
                }
                it.show(parentFragmentManager, "dialog")
            }
        }
    }

    override fun handleTask(input: Task) {
        argTask.apply {
            this.name = input.name
            this.drawableId = input.drawableId
            this.totalMtime = input.totalMtime
            this.oneCycleMtime = input.oneCycleMtime
            this.note = input.note
        }
        taskViewModel?.update(argTask)

        // 因为plan是多条数据，所以不能直接update，只能先delete，后insert了
        taskViewModel?.taskRepository?.deletePlansByTid(argTask.id!!)
        this.insertPlansByPlanInfoOnly(argTask.id!!, planInfoOnly)
    }

}
