package com.example.hours.home.dialog

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.hours.BeforeBackListener
import com.example.hours.Box
import com.example.hours.R
import kotlinx.android.synthetic.main.dialog_select_icon.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick

class SelectIconDialogFragment(var boxSelectDrawableId: Box<Int>): DialogFragment() {
    lateinit var myRadioTableLayoutManager: MyRadioTableLayoutManager

    var beforeBackListener: BeforeBackListener? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var rootView = inflater.inflate(R.layout.dialog_select_icon, container)

        this.myRadioTableLayoutManager = MyRadioTableLayoutManager(resources, rootView.tlTableLayout, boxSelectDrawableId)
        rootView.btnAddIcon.onClick {
            beforeBackListener?.beforeBack()
            this@SelectIconDialogFragment.dismiss()
        }

        return rootView
    }
}

    class MyRadioTableLayoutManager( var resources: Resources, var tableLayout: TableLayout, var boxSelectDrawableId: Box<Int>) {
        companion object {
            val drawableIds = arrayOf(
                R.drawable.ic_task1, R.drawable.ic_task2, R.drawable.ic_task3,
                R.drawable.ic_task4, R.drawable.ic_task5, R.drawable.ic_task6
            )
        }

        private var imageViewChildren = ArrayList<ImageView>()
        var imageViewSelected: ImageView
        init {
            initImageViewChildren()
            initState()

            this.imageViewSelected = getViewByDrawableId(boxSelectDrawableId.v)!!
            setViewSelectedByDrawableId(boxSelectDrawableId.v)
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

        fun getViewByDrawableId(drawableId: Int): ImageView? {
            for (imageView in this.imageViewChildren)
                if (imageView.tag == drawableId)
                    return imageView
            return null
        }

        fun setViewSelectedByDrawableId(drawableId: Int) {
            for (imageView in this.imageViewChildren)
                if (imageView.tag == drawableId)
                    setViewSelected(imageView)
        }

        private fun setViewSelectedEffect(imageView: ImageView) {
            var layers = arrayOfNulls<Drawable>(2)
            layers[0] = imageView.image
            layers[1] = resources.getDrawable(R.drawable.ic_task_border_shape, null)
            imageView.image = LayerDrawable(layers)
        }


        fun setViewSelected(view: View) {
            if ((view is ImageView) && (view.image is Drawable)) {
                // restore last iv image
                imageViewSelected?.let { it.image = resources.getDrawable(it.tag as Int, null) }
                // new clicked view
                imageViewSelected = view
                boxSelectDrawableId.v = imageViewSelected.tag as Int
                setViewSelectedEffect(view)
            }
        }

        private fun getTaskDrawableId(id: Int = 0): Int {
            return drawableIds[id % drawableIds.size]
        }

    }
