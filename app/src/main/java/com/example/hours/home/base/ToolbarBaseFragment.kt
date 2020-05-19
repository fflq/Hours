package com.example.hours.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hours.R
import kotlinx.android.synthetic.main.fragment_toolbar_base.view.*

abstract class ToolbarBaseFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val root = inflater.inflate(R.layout.fragment_toolbar_base, container, false)

        // 处理ellap和viewpager2之间的滑动冲突
        root.nested_scroll_view.isFillViewport = true

        return root
    }

}
