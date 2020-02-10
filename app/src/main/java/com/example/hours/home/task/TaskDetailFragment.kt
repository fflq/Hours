package com.example.hours.home.task


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.fragment_task_detail.*
import org.jetbrains.anko.image

/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : NotHomeBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val task = arguments?.getParcelable<Task>("task")
        if (task is Task) {
            tvTaskName.text = task.name
            ivTaskIcon.image = resources.getDrawable(task.drawableId, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_detail_menu_edit -> {
                this.navController?.navigate(R.id.action_taskDetailFragment_to_taskEditFragment, arguments)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
