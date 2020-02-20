package com.example.hours.home.task


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.hours.BeforeBackListener
import com.example.hours.Box
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.data.TaskPlan
import com.example.hours.home.data.TaskPlan.Companion.TYPE_DAY
import com.example.hours.home.data.TaskPlan.Companion.TYPE_WEEK
import com.example.hours.home.dialog.MyRadioTableLayoutManager
import com.example.hours.home.dialog.SelectIconDialogFragment
import com.example.hours.home.dialog.SelectPlanDialogFragment
import kotlinx.android.synthetic.main.fragment_task_add.*
import kotlinx.android.synthetic.main.fragment_task_add.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
open class TaskAddFragment : NotHomeBaseFragment() {
    private var planInfoOnly = TaskPlan.PlanInfoOnly()
    var boxSelectDrawableId = Box(MyRadioTableLayoutManager.mDrawableIds[0])

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.fragment_task_add, container, false)

        // 选择图标的dialogfragment
        rootView.ivIconSelected.image = resources.getDrawable(boxSelectDrawableId.v, null)
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

        // 选择计划的dialogfragment
        rootView.tvPlan.onClick {
            SelectPlanDialogFragment(planInfoOnly).also {
                it.beforeBackListener = object : BeforeBackListener {
                    override fun beforeBack() { rootView.tvPlan.text = planInfoOnly.toString() }
                }
                it.show(parentFragmentManager, "dialog")
            }
        }

        return rootView
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_add_menu, menu)
    }

    // 根据plan类型，插入数据到task_plan
    fun insertPlansByPlanInfoOnly(tid: Int, inPlanInfoOnly: TaskPlan.PlanInfoOnly) {
        var planList = ArrayList<TaskPlan>()
        when (inPlanInfoOnly.type) {
            TYPE_DAY -> inPlanInfoOnly.let { planList.add(TaskPlan(tid, it.type, 0, it.oneCycleDays, it.oneCycleMtime)) }
            TYPE_WEEK -> {
                for (dayOfWeek in inPlanInfoOnly.dayOfWeekList)
                    inPlanInfoOnly.let { planList.add (TaskPlan(tid, it.type, dayOfWeek, it.oneCycleDays, it.oneCycleMtime)) }
            }
        }
        taskViewModel?.taskRepository?.insertPlans(*planList.toArray( Array(planList.size, { TaskPlan()}) ))
    }

    // 处理add/edit提交的数据，分为task部分和task_plan部分
    open fun handleTask(input: Task) {
        val tid = taskViewModel?.taskRepository?.insertOne(input)?.toInt()!!
        insertPlansByPlanInfoOnly(tid, planInfoOnly)
    }

    private fun save() {
        fun isNotEmpty(): Boolean {
            return ( etTaskName.text.isNotEmpty() && etTaskTime.text.isNotEmpty() )
        }

        if ( isNotEmpty() ) {
            val task = Task().apply {
                name = etTaskName.text.toString()
                drawableId = boxSelectDrawableId.v
                totalMtime = etTaskTime.text.toString().toInt()*60
                note = etNote.text.toString()
            }
            handleTask(task)


            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etTaskName.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            navController?.navigateUp()
        }
        else    toast("please input all")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_menu_save -> save ()

        }
        return super.onOptionsItemSelected(item)
    }

}

