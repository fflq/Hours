package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hours.Box
import com.example.hours.R
import kotlinx.android.synthetic.main.dialog_select_time.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class SelectTimeDialogFragment(var boxMtime: Box<Int>): DialogFragment() {
    companion object {
        val hourList = ArrayList((0..24).map { i->i.toString() })
        val minuteList = ArrayList((0..59 step 5).map { i->i.toString() })
        //val minuteList = arrayListOf("0", "15", "30", "45")
    }

    var onSubmitListener: View.OnClickListener? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_select_time, container)

        var hour = boxMtime.v / 60
        var minute = boxMtime.v % 60

        rootView.lvHour.apply {
            setItems(hourList)
            setListener { hour = it }
            setCurrentPosition(hour)
            setNotLoop()
        }
        rootView.lvMinute.apply {
            setItems(minuteList)
            setListener { minute = it*5 }
            setCurrentPosition(minute/5)
            setNotLoop()
        }

        rootView.btnSubmitPlan.onClick {
            this@SelectTimeDialogFragment.apply {
                boxMtime.v = hour*60 + minute
                onSubmitListener?.onClick(rootView)
                dismiss()
            }
        }

        return rootView
    }
}