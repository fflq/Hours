package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.hours.BeforeBackListener
import com.example.hours.R
import com.example.hours.home.data.TaskPlan
import com.example.hours.home.data.TaskPlan.Companion.TYPE_DAY
import com.example.hours.home.data.TaskPlan.Companion.TYPE_WEEK
import kotlinx.android.synthetic.main.dialog_select_plan.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SelectPlanDialogFragment(var planInfoOnly: TaskPlan.PlanInfoOnly): DialogFragment() {

    companion object {
        val dayList = ArrayList((0..30).map { i -> i.toString() })  // 0 1 2 ... 30
        val htimeList = ArrayList((0..48 step 1).map { i -> (i.toDouble()/2).toString() })  // 0 0.5 1 1.5 ... 24
    }

    var beforeBackListener: BeforeBackListener? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_select_plan, container)

        // 先设置radiogroup监听
        rootView.rgDayWeek.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDay -> {
                    rootView.cltDay.visibility = View.VISIBLE
                    rootView.cltWeek.visibility = View.GONE
                    planInfoOnly.type = TYPE_DAY
                }
                R.id.rbWeek -> {
                    rootView.cltDay.visibility = View.GONE
                    rootView.cltWeek.visibility = View.VISIBLE
                    planInfoOnly.type = TYPE_WEEK
                }
            }
        }
        // 后设置radiobutton状态
        when (planInfoOnly.type) {
            TYPE_DAY -> rootView.rbDay.isChecked = true
            TYPE_WEEK -> {
                rootView.rbWeek.isChecked = true
                for (cb in rootView.cltWeek.children)
                    if (cb is CheckBox) {
                        var dayOfWeek = cb.tag.toString().toInt()
                        if (planInfoOnly.dayOfWeekList.contains(dayOfWeek))
                            cb.isChecked = true
                    }
            }
        }

        // 初始化loopview
        rootView.lvDay.apply {
            setItems(dayList)
            setListener { planInfoOnly.cycleDays = it }
            setCurrentPosition(planInfoOnly.cycleDays)
            setNotLoop()
        }
        rootView.lvHtime.apply {
            setItems(htimeList)
            setListener { planInfoOnly.cycleMtime = it*30 }
            setCurrentPosition(planInfoOnly.cycleMtime/30)
            setNotLoop()
        }

        // 提交整合数据
        rootView.btnSubmitPlan.onClick {
            planInfoOnly.dayOfWeekList.clear()
            // week要额外处理dayOfWeekList，week可能是多选，要生成多个info方便别的逻辑处理
            if (planInfoOnly.type == TYPE_WEEK) {
                // 遍历所有选中的checkbox，添加进list中
                for (cb in rootView.cltWeek.children)
                    if (cb is CheckBox && cb.isChecked)
                        planInfoOnly.dayOfWeekList.add(cb.tag.toString().toInt())
            }

            this@SelectPlanDialogFragment.apply {
                beforeBackListener?.beforeBack()
                dismiss()
            }
        }

        return rootView
    }
}