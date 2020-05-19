package com.example.hours

import android.os.Bundle
import com.example.hours.home.base.ToolbarBaseActivity
import kotlinx.android.synthetic.main.fragment_toolbar_base.*
import kotlinx.android.synthetic.main.toolbar_normal.*

class PersonActivity : ToolbarBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_toolbar_base)
        parent.viewstub_toolbar_normal.inflate()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
