package com.example.hours.home.base

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_toolbar_base.*
import kotlinx.android.synthetic.main.toolbar_normal.*

open class ToolbarNormalActivity : ToolbarBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parent.viewstub_toolbar_normal.inflate()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
