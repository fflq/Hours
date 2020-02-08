package com.example.hours.home.task


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hours.R
import com.example.hours.home.data.TaskViewModel

/**
 * A simple [Fragment] subclass.
 */
open class TaskBaseFragment : Fragment() {

    var mNavController: NavController? = null
    var mTaskViewModel: TaskViewModel? = null


    open fun initVarOnce() {
        mNavController = mNavController?: findNavController()
        mTaskViewModel = mTaskViewModel?: ViewModelProvider(activity!!)[TaskViewModel::class.java]
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        }

        initVarOnce()
    }


}
