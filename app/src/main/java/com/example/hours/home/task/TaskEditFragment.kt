package com.example.hours.home.task


import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hours.home.base.TaskModifyBaseFragment
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.fragment_task_modify.view.*
import org.jetbrains.anko.image

/**
 * A simple [Fragment] subclass.
 */
class TaskEditFragment : TaskModifyBaseFragment() {
    lateinit var argTask: Task

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = super.onCreateView(inflater, container, savedInstanceState)!!

        // 初始化和Dialog共享的数据
        this.argTask = arguments?.getParcelable("task")!!
        this.boxSelectDrawableId.v = argTask.drawableId
        this.planInfoOnly.setPlanInfoByTask(argTask)

        // 填充界面数据
        rootView.apply {
            etTaskName.text = SpannableStringBuilder(argTask.name)
            etTaskTime.text = SpannableStringBuilder((argTask.totalMtime / 60).toString())
            etNote.text = SpannableStringBuilder(argTask.note)
            ivIconSelected.image = resources.getDrawable(boxSelectDrawableId.v, null)
            tvPlan.text = planInfoOnly.toString()
        }

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    // 为了能up后能在detail中显示，需要更新同一地址的argtask，而不能是inTask这个新东西
    override fun handleTask(inTask: Task) {
        argTask.apply {
            name = inTask.name
            drawableId = inTask.drawableId
            totalMtime = inTask.totalMtime
            note = inTask.note

            setTaskByPlanInfo(planInfoOnly)
        }
        taskViewModel?.update(argTask)
    }

}
