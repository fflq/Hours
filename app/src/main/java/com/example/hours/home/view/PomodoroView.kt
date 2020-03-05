package com.example.hours.home.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.hours.R
import kotlin.math.max
import kotlin.math.min

class PomodoroView : View {

    private var circleColor: Int = 0xC0C0C0
    private var arcColor: Int = Color.RED
    private var strokeWidth: Int = 10

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PomodoroView)
        styledAttributes.let {
            circleColor = it.getColor(R.styleable.PomodoroView_circle_color, Color.GRAY)
            arcColor = it.getColor(R.styleable.PomodoroView_arc_color, Color.RED)
            strokeWidth = it.getInt(R.styleable.PomodoroView_stroke_width, strokeWidth)
            it.recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // get default dp
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val dpDisplayWidth = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        val dpDisplayHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()
        // 屏幕长宽的最小值，因为要画圆
        val dpSize = min(dpDisplayWidth, dpDisplayHeight)
        Log.d ("FF", dpSize.toString())

        val defaultWidth = (0.8 * dpSize).toInt()
        var width = getSize(widthMeasureSpec, defaultWidth, dpSize)
        var height = getSize(heightMeasureSpec, defaultWidth, dpSize)
        Log.d ("FF", "$width $height")

        setMeasuredDimension(max(width, height), max(width, height))
    }

    private fun getSize(measureSpec: Int, defaultSize: Int, atMostSize: Int): Int {
        val size = MeasureSpec.getSize(measureSpec)
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.EXACTLY -> min(size, atMostSize)
            MeasureSpec.AT_MOST -> atMostSize
            //MeasureSpec.UNSPECIFIED
            else -> min(defaultSize, atMostSize)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 需要剪去线宽，不然超出布局会不显示
        val needSub = 2*strokeWidth
        val radius = ((measuredWidth-needSub) / 2).toFloat()
        val oXY = (measuredWidth / 2).toFloat()

        // 圆弧所在的rect
        val oval = RectF().also {
            it.left = width/2 - radius
            it.right = width - it.left
            it.top = height/2 - radius
            it.bottom = height - it.top
        }

        val paintBackGround = Paint().also {
            it.isAntiAlias = true
            it.color = circleColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = strokeWidth.toFloat()
        }
        canvas?.drawCircle(oXY, oXY, radius, paintBackGround)

        paintBackGround.color = arcColor
        canvas?.drawArc(oval, -90f, 30f, false, paintBackGround)
    }

}