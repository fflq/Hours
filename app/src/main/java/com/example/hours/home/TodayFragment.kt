package com.example.hours.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import com.example.hours.R
import com.example.hours.home.task.TaskBaseFragment
import kotlinx.android.synthetic.main.nav.*

class TodayFragment : TaskBaseFragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // toolbar's button for opening drawer
            android.R.id.home -> activity?.drawerlayout?.openDrawer(GravityCompat.START)
        }
        return true
    }

}