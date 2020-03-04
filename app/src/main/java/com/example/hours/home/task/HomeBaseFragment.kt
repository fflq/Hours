package com.example.hours.home.task

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.dialog.SortDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.layout_home.*
import org.jetbrains.anko.sdk27.coroutines.onQueryTextListener

abstract class HomeBaseFragment: BaseFragment() {

    var taskAdapter: TaskAdapter? = null


    abstract val toDetailAction: Int
    abstract val toAddTimeAction: Int
    abstract val menuLayout: Int
    abstract val taskAdapterType: TaskAdapter.TYPE
    abstract val liveTasks: LiveData<List<Task>>?

    abstract fun selectByNameLike(name: String): LiveData<List<Task>>?


    override fun initVarOnce() {
        super.initVarOnce()

        this.taskAdapter = this.taskAdapter?: TaskAdapter(context, taskAdapterType).also {
            it.onItemClickListener = object: TaskAdapter.OnTaskClickListener {
                override fun onClick(v: View?, task: Task) {
                    var bundle = Bundle().apply { putParcelable("task", task) }
                    navController?.navigate(toDetailAction, bundle)
                }
            }
            it.onItemAddTimeClickListener = object : TaskAdapter.OnTaskClickListener {
                override fun onClick(v: View?, task: Task) {
                    var bundle = Bundle().apply { putParcelable("task", task) }
                    navController?.navigate(toAddTimeAction, bundle)
                }
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.bottom_nav_view?.visibility = View.VISIBLE

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        }

        srlSwipeRefresh.setOnRefreshListener {
            srlSwipeRefresh.isRefreshing = true
            Handler().postDelayed({ srlSwipeRefresh.isRefreshing = false }, 1000)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        updateLiveTasksObserve(liveTasks)

        taskViewModel?.liveSort?.observe(viewLifecycleOwner, Observer<String> {
            Log.d ("FLQ", "observe")
            taskViewModel?.sharedPreferencesEditor?.apply {
                putString(getString(R.string.sort), it).commit()
            }
            taskViewModel?.notifySortChanged()

            updateLiveTasksObserve(liveTasks)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(UP or DOWN, START or END) {
            override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = viewHolder.itemView.tag as Task
                if (task is Task) {
                    AlertDialog.Builder(activity!!).apply {
                        setPositiveButton("OK") { _: DialogInterface, _: Int ->
                            taskViewModel?.delete(task)
                        }
                        setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                            taskAdapter?.notifyDataSetChanged()
                        }
                        setTitle("delete the task?").create().show()
                    }
                }
            }
        }).attachToRecyclerView(recyclerview)
    }


    // update observe with newLiveTasks, and remove old-
    private var oldLiveTasks: LiveData<List<Task>>? = null
    fun updateLiveTasksObserve(newLiveTasks: LiveData<List<Task>>?) {
        oldLiveTasks?.removeObservers(viewLifecycleOwner)
        oldLiveTasks = newLiveTasks
        newLiveTasks?.observe(viewLifecycleOwner, Observer<List<Task>> {
            this.taskAdapter?.submitList(it)
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // toolbar's button for opening drawer
            android.R.id.home -> activity?.drawerlayout?.openDrawer(GravityCompat.START)
            R.id.menu_sort -> SortDialogFragment().show(parentFragmentManager, "dialog")
        }
        return super.onOptionsItemSelected(item)
    }


    fun onSearch(searchView: SearchView) {
        searchView.onQueryTextListener {
            onQueryTextChange {
                val input = searchView.query.toString().trim()
                updateLiveTasksObserve(selectByNameLike(input))
                true
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(menuLayout, menu)

        var searchView = menu?.findItem(R.id.app_bar_search).actionView as SearchView
        if (searchView is SearchView)   onSearch(searchView)
    }

}