package com.example.hours.home.task


import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.hours.R
import com.example.hours.home.data.Task
import kotlinx.android.synthetic.main.fragment_task_add.*
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
open class TaskAddFragment : NotHomeBaseFragment() {
    lateinit var myRadioTableLayoutManager: MyRadioTableLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_add, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.myRadioTableLayoutManager = MyRadioTableLayoutManager(resources, tlTableLayout)
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

        if ( isNotEmpty() && (tlTableLayout.tag != null)) {
            val input = Task().apply {
                name = etTaskName.text.toString()
                drawableId = (tlTableLayout.tag as ImageView).tag as Int
                totalMtime = etTaskTime.text.toString().toInt()*60
                oneCycleMtime = etTimePlan.text.toString().toInt()
                cycleDays = etIntervalDay.text.toString().toInt()
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


class MyRadioTableLayoutManager(var resources: Resources, var tableLayout: TableLayout) {

    private var imageViewChildren = ArrayList<ImageView>()

    companion object{
        val mDrawableIds = arrayOf(R.drawable.ic_task1, R.drawable.ic_task2, R.drawable.ic_task3,
            R.drawable.ic_task4, R.drawable.ic_task5, R.drawable.ic_task6)
    }

    init {
        initImageViewChildren()
        initState()
        setViewSelected(this.imageViewChildren[0])
    }


    private fun initImageViewChildren() {
        for (tableRow in this.tableLayout.children)
            if (tableRow is TableRow)
                for (imageView in tableRow.children)
                    if (imageView is ImageView)
                        imageViewChildren.add(imageView)
    }


    // set imageview in tableLayout onClick func
    private fun initState() {
        for ((id, imageView) in this.imageViewChildren.withIndex()) {
            val drawableId = getTaskDrawableId(id)
            imageView.apply {
                setImageDrawable(resources.getDrawable(drawableId, null))
                tag = drawableId
                setOnClickListener { setViewSelected(it) }
            }
        }
    }


    fun selectByDrawableId(drawableId: Int) {
        for (imageView in this.imageViewChildren)
            if (imageView.tag == drawableId)
                setViewSelected(imageView)
    }

    private fun setViewSelectedImage(imageView: ImageView) {
        var layers = arrayOfNulls<Drawable>(2)
        layers[0] = imageView.image
        layers[1] = resources.getDrawable(R.drawable.ic_task_border_shape, null)
        imageView.image = LayerDrawable(layers)
    }


    fun setViewSelected(view: View) {
        if ( (view is ImageView) && (view.image is Drawable) ) {
            // restore last iv image
            var lastSelectImageView = this.tableLayout.tag
            if (lastSelectImageView is ImageView)
                lastSelectImageView.image = resources.getDrawable(lastSelectImageView.tag as Int, null)

            // new clicked view
            this.tableLayout.tag = view
            setViewSelectedImage (view)
        }
    }

    private fun getTaskDrawableId(id: Int=0): Int{
        return mDrawableIds[id % mDrawableIds.size]
    }

}
