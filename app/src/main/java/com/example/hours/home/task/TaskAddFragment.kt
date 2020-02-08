package com.example.hours.home.task


import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_task_add.*
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class TaskAddFragment : TaskBaseFragment() {

    companion object{
        val mDrawableIds = arrayOf<Int>(R.drawable.ic_task1, R.drawable.ic_task2, R.drawable.ic_task3,
            R.drawable.ic_task4, R.drawable.ic_task5, R.drawable.ic_task6)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_add, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setIconSelect ()

        // set 'up' button icon
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)
        // hide bottom nav
        activity?.bottom_nav_view?.visibility = View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.task_add_toolbar_menu, menu)
        //activity?.invalidateOptionsMenu()
    }


    private fun addTask() {
        if ( etTaskName.text.isNotEmpty() && etTaskTime.text.isNotEmpty() && (tlTableLayout.tag != null)) {
            val drawableId = (tlTableLayout.tag as ImageView).tag as Int
            mTaskViewModel?.insert( Task( etTaskName.text.toString(), etTaskTime.text.toString().toInt(), drawableId) )
            mNavController?.navigateUp()
        }
        else
            toast("please input all")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d ("FLQ", "taskadd oois")
        when (item.itemId) {
            R.id.task_toolbar_menu_save -> addTask ()

            // override TaskBaseFragment op
            android.R.id.home -> mNavController?.navigateUp()
        }

        return false
    }


    private fun setViewSelected(imageView: ImageView) {
        var layers = arrayOfNulls<Drawable>(2)
        layers[0] = imageView.image
        layers[1] = resources.getDrawable(R.drawable.ic_task_border_shape, null)
        imageView.image = LayerDrawable(layers)
    }


    fun onImageViewSelect(view: View) {
        if ( (view is ImageView) && (view.image is Drawable) ) {
            // restore last iv image
            var lastSelectImageView = tlTableLayout.tag
            if (lastSelectImageView is ImageView)
                lastSelectImageView.image = resources.getDrawable(lastSelectImageView.tag as Int, null)

            // new clicked view
            tlTableLayout.tag = view
            setViewSelected (view)
        }
    }

    override fun onResume() {
        activity?.bottom_nav_view?.visibility = View.VISIBLE
        super.onResume()
    }

    private fun getTaskDrawableId(id: Int=0): Int{
        return mDrawableIds[id % mDrawableIds.size]
    }

    // set imageview in tableLayout onClick func
    private fun setIconSelect() {
        var id = 0
        var isFirst = true

        for (tableRow in tlTableLayout.children) {
            if (tableRow is TableRow) {
                for (imageView in tableRow.children)
                    if (imageView is ImageView) {
                        // set first checked
                        if (isFirst) { onImageViewSelect(imageView); isFirst = false }

                        val drawableId = getTaskDrawableId(id++)
                        imageView.apply {
                            setImageDrawable(resources.getDrawable(drawableId, null))
                            tag = drawableId
                            setOnClickListener { onImageViewSelect(it) }
                        }
                    }
            }
        }
    }

}
