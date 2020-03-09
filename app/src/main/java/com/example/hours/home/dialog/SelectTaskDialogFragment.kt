package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hours.R
import com.example.hours.home.data.Task

class SelectTaskDialogFragment(var taskSelected: Task?): DialogFragment() {

    var onSubmitListener: View.OnClickListener? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.dialog_select_task, container, false)

        return root
    }
}