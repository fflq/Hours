package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.hours.R
import kotlinx.android.synthetic.main.dialog_select_time.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class SelectTimeDialogFragment(var tvSelectTime: TextView): DialogFragment() {
    companion object {
        val hourList = ArrayList((0..24).map { i->i.toString() })
        val minuteList = ArrayList((0..59 step 5).map { i->i.toString() })
        //val minuteList = arrayListOf("0", "15", "30", "45")
    }

    var hour: Int = 0
    var minute: Int = 0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_select_time, container)

        if (tvSelectTime.tag == null)   tvSelectTime.tag = 0
        val mtime = tvSelectTime.tag as Int
        hour = mtime / 60
        minute = mtime % 60

        rootView.lvHour.apply {
            setListener { hour = it }
            setCurrentPosition(hour)
            setNotLoop()
            setItems(hourList)
        }
        rootView.lvMinute.apply {
            setListener { minute = it*5 }
            setCurrentPosition(minute/5)
            setNotLoop()
            setItems(minuteList)
        }

        rootView.btnSubmitPlan.onClick {
            this@SelectTimeDialogFragment.apply {
                tvSelectTime.tag = hour*60 + minute
                tvSelectTime.text = "$hour h $minute m"
                dismiss()
            }
        }

        return rootView
    }
}