package com.example.hours.home.base


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
import com.example.hours.home.dialog.MyRadioTableLayoutManager
import com.example.hours.home.dialog.SelectIconDialogFragment
import com.example.hours.home.dialog.SelectPlanDialogFragment
import kotlinx.android.synthetic.main.fragment_task_modify.*
import kotlinx.android.synthetic.main.fragment_task_modify.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
abstract class TaskModifyBaseFragment : NotHomeBaseFragment() {

    // 从task中提取的单独的plan信息，用于和PlanDialog共享的
    var planInfoOnly = TaskPlan.PlanInfoOnly()
    // 从task中提取的单独的图标号，用于和IconDialog共享的
    var boxSelectDrawableId = Box(MyRadioTableLayoutManager.drawableIds[0])

    abstract fun handleTask(inTask: Task)

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.fragment_task_modify, container, false)

        // 选择图标的dialogfragment
        rootView.ivIconSelected.image = resources.getDrawable(boxSelectDrawableId.v, null)
        rootView.cltSelectIcon.onClick {
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
        inflater.inflate(R.menu.task_save_menu, menu)
    }


    // 获取add/edit提交的数据，分为task部分和task_plan部分
    private fun getTaskData(inPlanInfoOnly: TaskPlan.PlanInfoOnly): Task {
        return Task().apply {
            // id = ?, set in subclass
            name = etTaskName.text.toString()
            drawableId = boxSelectDrawableId.v
            totalMtime = etTaskTime.text.toString().toInt()*60
            note = etNote.text.toString()

            setTaskByPlanInfo(planInfoOnly)
        }
    }

    private fun save() {
        fun isNotEmpty(): Boolean {
            return ( etTaskName.text.isNotEmpty() && etTaskTime.text.isNotEmpty() )
        }

        if ( isNotEmpty() ) {
            handleTask(getTaskData(planInfoOnly))

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

