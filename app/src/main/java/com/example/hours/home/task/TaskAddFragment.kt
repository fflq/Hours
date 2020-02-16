package com.example.hours.home.task


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.hours.R
import com.example.hours.home.data.Task
import com.example.hours.home.dialog.MyRadioTableLayoutManager
import com.example.hours.home.dialog.SelectIconDialogFragment
import kotlinx.android.synthetic.main.fragment_task_add.*
import kotlinx.android.synthetic.main.fragment_task_add.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
open class TaskAddFragment : NotHomeBaseFragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.fragment_task_add, container, false)

        // init default selected icon
        MyRadioTableLayoutManager.mDrawableIds[0].let {
            rootView.ivIconSelected.tag = it
            rootView.ivIconSelected.image = resources.getDrawable(it, null)
        }
        rootView.clt141.onClick {
            SelectIconDialogFragment(rootView.ivIconSelected).show(parentFragmentManager, "dialog")
        }

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_add_menu, menu)
    }


    // different in add/edit
    open fun handleTask(input: Task) {
        taskViewModel?.insert(input)
    }

    private fun save() {
        fun isNotEmpty(): Boolean {
            return ( etTaskName.text.isNotEmpty() && etTaskTime.text.isNotEmpty() && etTimePlan.text.isNotEmpty()
                    && etIntervalDay.text.isNotEmpty()
                    )
        }

        if ( isNotEmpty() && (ivIconSelected.tag != null)) {
            val input = Task().apply {
                name = etTaskName.text.toString()
                drawableId = ivIconSelected.tag as Int
                totalMtime = etTaskTime.text.toString().toInt()*60
                oneCycleMtime = etTimePlan.text.toString().toInt()
                cycleDays = etIntervalDay.text.toString().toInt()
                note = etNote.text.toString()
            }

            handleTask(input)

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etTaskName.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            navController?.navigateUp()
        }
        else    toast("please input all")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_menu_save -> save ()

        }
        return super.onOptionsItemSelected(item)
    }

}

