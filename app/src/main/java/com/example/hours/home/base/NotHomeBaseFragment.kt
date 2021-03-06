package com.example.hours.home.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_home.*

open class NotHomeBaseFragment: BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.bottom_nav_view?.visibility = View.GONE

        // set 'up' button icon
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)
        // hide bottom activity_main
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}