package com.example.hours.home.task


import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.fragment_task_add.*

/**
 * A simple [Fragment] subclass.
 */
class TaskEditFragment : TaskAddFragment() {
    lateinit var argTask: Task

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        argTask = arguments?.getParcelable<Task>("task")!!
        if (argTask is Task) {
            tvTaskName.text = SpannableStringBuilder(argTask.name)
            etTaskTime.text = SpannableStringBuilder((argTask.mtime / 60).toString())
            this.myRadioTableLayoutManager.selectByDrawableId(argTask.drawableId)
        }
    }

    override fun handleTask(name: String, mtime: Int, drawableId: Int) {
        argTask.apply {
            this.name = name
            this.mtime = mtime
            this.drawableId = drawableId
        }
        taskViewModel?.update(argTask)
    }

}
