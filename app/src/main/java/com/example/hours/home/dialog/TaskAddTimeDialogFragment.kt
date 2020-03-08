package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hours.Box
import com.example.hours.R
import kotlinx.android.synthetic.main.fragment_task_add_time.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TaskAddTimeDialogFragment(var boxMtime: Box<Int>): DialogFragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.fragment_task_add_time, container)

        root.tvSelectTime.onClick {
            SelectTimeDialogFragment(boxMtime).let {
                it.onSubmitListener = View.OnClickListener {
                    root.tvSelectTime.text = "${boxMtime.v/60}h ${boxMtime.v%60}m"
                }
                it.show(parentFragmentManager, "dialog")
            }
        }

        return root
    }
}