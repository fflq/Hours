package com.example.hours.home.task

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hours.R
import kotlinx.android.synthetic.main.nav.*
import kotlinx.android.synthetic.main.task_fragment.*

open class HomeBaseFragment: BaseFragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // toolbar's button
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_home_black_24dp)
        }

        srlSwipeRefresh.setOnRefreshListener {
            srlSwipeRefresh.isRefreshing = true
            Handler().postDelayed({ srlSwipeRefresh.isRefreshing = false }, 1000)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // toolbar's button for opening drawer
            android.R.id.home -> activity?.drawerlayout?.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}