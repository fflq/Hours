package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hours.Box
import com.example.hours.R
import kotlinx.android.synthetic.main.dialog_select_one.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SelectOneDialogFragment(private var boxOne: Box<Int>, private var listOne: List<String>): DialogFragment() {

    var onSubmitListener: View.OnClickListener? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_select_one, container)

        rootView.lvOne.apply {
            setNotLoop()
            setItems(listOne)
            setListener { boxOne.v = it+1 }
            setCurrentPosition(boxOne.v-1)
        }

        rootView.btnSubmit.onClick {
            onSubmitListener?.onClick(rootView)
            dismiss()
        }

        return rootView
    }
}

