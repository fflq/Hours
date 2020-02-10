package com.example.hours.home.task

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

open class NotHomeBaseFragment: BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // set 'up' button icon
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)
        // hide bottom nav
        activity?.bottom_nav_view?.visibility = View.GONE
    }


    override fun onResume() {
        // restore bottom nav
        activity?.bottom_nav_view?.visibility = View.VISIBLE
        super.onResume()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navController?.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}