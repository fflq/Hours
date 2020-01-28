package com.example.hours.home.task

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hours.R
import kotlinx.android.synthetic.main.activity_task_add.*
import org.jetbrains.anko.toast

class TaskAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)

        task_add_btn.setOnClickListener {
            if (task_name_tv.text.isEmpty())
                toast("please input text")
            else {
                setResult(Activity.RESULT_OK, Intent().putExtra("name", task_name_tv.text.toString()))
                //onBackPressed()
                finish()
            }
        }
    }
}
