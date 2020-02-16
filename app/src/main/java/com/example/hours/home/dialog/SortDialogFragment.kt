package com.example.hours.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.hours.R
import com.example.hours.home.data.TaskViewModel
import kotlinx.android.synthetic.main.dialog_sort.view.*

class SortDialogFragment: DialogFragment() {

    var sortChildren = ArrayList<RadioButton>()
    lateinit var taskViewModel: TaskViewModel


    private fun initRadioGroupChildren(radioGroup: RadioGroup, arrayList: ArrayList<RadioButton>) {
        for (radioButton in radioGroup.children)
            if (radioButton is RadioButton)
                arrayList.add(radioButton)
    }


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_sort, container)

        taskViewModel = ViewModelProvider(activity!!)[TaskViewModel::class.java]

        initRadioGroupChildren(rootView.rgSort, sortChildren)

        var sort = taskViewModel.liveSort.value as String
        this.sortChildren.forEach { it.isChecked = (sort == (it.tag as String)) }

        rootView.rgSort.setOnCheckedChangeListener { group, checkedId ->
            val sort = rootView.findViewById<RadioButton>(checkedId).tag as String
            taskViewModel.liveSort.value = sort
        }

        return rootView
    }
}