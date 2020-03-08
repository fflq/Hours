package com.example.hours.home.task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hours.Box
import com.example.hours.R
import com.example.hours.home.dialog.SelectOneDialogFragment
import com.example.hours.home.dialog.TaskAddTimeDialogFragment
import com.example.hours.home.view.PomodoroView
import kotlinx.android.synthetic.main.fragment_pomodoro.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class PomodoroFragment : Fragment() {

    private val listTimes = ArrayList((1..99).map { i->i.toString() })
    private var boxTime = Box(1)

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.fragment_pomodoro, container, false)

        root.pomodoroview.onClick {
            // 未开始，则弹窗
            SelectOneDialogFragment(boxTime, listTimes).also {
                it.onSubmitListener = View.OnClickListener {
                    root.pomodoroview.time = 60*boxTime.v.toLong()
                    toast(root.pomodoroview.time.toString())
                }
                it.show(parentFragmentManager, "dialog")
            }
        }
        // 番茄钟结束
        root.pomodoroview.onFinishListener = View.OnClickListener {
            root.btnStartStop.text = "start"
            TaskAddTimeDialogFragment(boxTime).let {
                it.show(parentFragmentManager, "dialog")
            }
        }

        // 根据番茄钟状态设置btn文字
        root.btnStartStop.onClick {
            root.pomodoroview.let {
                when (it.status) {
                    PomodoroView.STATUS.STOPED -> {
                        if (it.start())
                            root.btnStartStop.text = "stop"
                        else {}
                    }

                    PomodoroView.STATUS.RUNNING -> {
                        AlertDialog.Builder(context).apply {
                            setMessage("Are u sure to stop?")
                            setNegativeButton("No") { _, _ -> }
                            setPositiveButton("Yes") { _, _ ->
                                if (it.stop())  root.btnStartStop.text = "start"
                            }
                        }.show()
                    }
                }
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
