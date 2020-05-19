package com.example.hours.home.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hours.R
import kotlinx.android.synthetic.main.fragment_toolbar_base.*

open class ToolbarBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_toolbar_base)

        nested_scroll_view.isFillViewport = true
    }

}
