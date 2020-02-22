package com.example.hours.home.task


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hours.home.data.Task

/**
 * A simple [Fragment] subclass.
 */
open class TaskAddFragment : TaskModifyBaseFragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = super.onCreateView(inflater, container, savedInstanceState)!!

        return rootView
    }

    override fun handleTask(inTask: Task) { taskViewModel?.insert(inTask) }

}

