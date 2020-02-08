package com.example.hours.home.task

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.nav.*
import kotlinx.android.synthetic.main.task_fragment.*

class TaskFragment : TaskBaseFragment() {
    var mTaskAdapter: TaskAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val rootView = inflater.inflate(R.layout.task_fragment, container, false)

        setHasOptionsMenu(true)
        return rootView
    }

    override fun initVarOnce() {
        super.initVarOnce()

        mTaskAdapter = mTaskAdapter?: TaskAdapter(resources)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        activity?.actionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        }
         */

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mTaskAdapter
        }

        mTaskViewModel?.mLiveWords?.observe(viewLifecycleOwner, Observer<List<Task>> {
            mTaskAdapter?.submitList(it)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_toolbar_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d ("FLQ", "task oois")
        when (item.itemId) {
            R.id.task_toolbar_menu_add -> mNavController?.navigate(R.id.action_taskFragment_to_taskAddFragment)

            // toolbar's button for opening drawer
            android.R.id.home -> activity?.drawerlayout?.openDrawer(GravityCompat.START)
        }

        return true
    }

}
