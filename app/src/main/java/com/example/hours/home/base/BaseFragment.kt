package com.example.hours.home.base


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hours.home.data.TaskViewModel

/**
 * A [Fragment] subclass.
 * base for
 */
abstract class BaseFragment : Fragment() {

    var navController: NavController? = null
    var taskViewModel: TaskViewModel? = null


    open fun initVarOnce() {
        navController = navController?: findNavController()
        taskViewModel = taskViewModel?: ViewModelProvider(requireActivity())[TaskViewModel::class.java]
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        initVarOnce()
    }

}
