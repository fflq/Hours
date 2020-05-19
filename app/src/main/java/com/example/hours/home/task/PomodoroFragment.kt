package com.example.hours.home.task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hours.Box
import com.example.hours.R
import com.example.hours.home.dialog.PomodoroAddTimeDialogFragment
import com.example.hours.home.dialog.SelectOneDialogFragment
import com.example.hours.home.view.PomodoroView.STATUS
import com.example.hours.home.viewmodel.PomodoroViewModel
import kotlinx.android.synthetic.main.fragment_pomodoro.*
import kotlinx.android.synthetic.main.fragment_pomodoro.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class PomodoroFragment : Fragment() {

    private val listTimes = ArrayList((1..99).map { i->i.toString() })
    private var boxTime = Box(1)    // first position
    private val pomodoroViewModel by viewModels<PomodoroViewModel>({requireActivity()})

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.fragment_pomodoro, container, false)

        // 从viewmodel获取中断的view数据
        //toast("${pomodoroViewModel.time} ${pomodoroViewModel.countDownTime}")
        root.pomodoroview.time = pomodoroViewModel.time
        root.pomodoroview.countDownTime = pomodoroViewModel.countDownTime
        // 若中断前还在运行，则恢复运行
        if ((root.pomodoroview.time != 0L) and (pomodoroViewModel.status == STATUS.RUNNING) )
            if (root.pomodoroview.start())
                root.btnStartStop.text = "stop"

        // 设置pview点击时，弹窗选择时间
        root.pomodoroview.onClick {
            // 未开始，则弹窗
            SelectOneDialogFragment(boxTime, listTimes).also {
                it.onSubmitListener = View.OnClickListener {
                    pomodoroViewModel.countDownTime = 6*boxTime.v.toLong()
                    root.pomodoroview.time = pomodoroViewModel.countDownTime
                    toast(root.pomodoroview.time.toString())
                }
                it.show(parentFragmentManager, "dialog")
            }
        }

        // 番茄钟结束时，弹窗选择添加到指定task时间中
        root.pomodoroview.onFinishListener = View.OnClickListener {
            root.btnStartStop.text = "start"
            PomodoroAddTimeDialogFragment(boxTime.v).let {
                it.show(parentFragmentManager, "dialog")
            }
        }

        // 根据番茄钟状态设置btn文字
        root.btnStartStop.onClick {
            root.pomodoroview.let {
                when (it.status) {
                    // 若pview本是stoped状态，click后则start，start成功则更改text并记录状态
                    STATUS.STOPED -> {
                        if (it.start()) {
                            root.btnStartStop.text = "stop"
                            pomodoroViewModel.status = STATUS.RUNNING
                        }
                        else {}
                    }

                    STATUS.RUNNING -> {
                        AlertDialog.Builder(context).apply {
                            setMessage("Are u sure to stop?")
                            setNegativeButton("No") { _, _ -> }
                            setPositiveButton("Yes") { _, _ ->
                                if (it.stop()) {
                                    root.btnStartStop.text = "start"
                                    pomodoroViewModel.status = STATUS.STOPED
                                }
                            }
                        }.show()
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 其他数据则是动态更新保存的
        pomodoroViewModel.time = pomodoroview.time
        // 避免里面timer等线程还在跑
        pomodoroview.stop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
