package com.example.hours.home.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import com.example.hours.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


class PomodoroView : View {

    private var circleColor: Int = resources.getColor(R.color.pomodoro_circle, null)
    private var arcColor: Int = resources.getColor(R.color.pomodoro_arc, null)
    private var strokeWidth: Int = 10
    private var scale = 0f

    var countDownTime = 0L
    var time = 0L
    set(value) {
        field = value
        invalidate()
    }

    // this的原点，半径，长宽
    private var oXY = 0F
    private var radius = 0F
    private var size = 0
    var onFinishListener: OnClickListener? = null
    var countDownTimer: CountDownTimer? = null
    var valueAnimator: ValueAnimator? = null

    var isStarted = false
    set(value) {
        field = value
        if (!field) {
            time = 0L
            scale = 0f
        }
    }

    var status = STATUS.STOPED


    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PomodoroView)
        styledAttributes.let {
            circleColor = it.getColor(R.styleable.PomodoroView_circle_color, circleColor)
            arcColor = it.getColor(R.styleable.PomodoroView_arc_color, arcColor)
            strokeWidth = it.getInt(R.styleable.PomodoroView_stroke_width, strokeWidth)
            it.recycle()
        }


    }

    // 是否包含在圆内
    private fun isContained(x: Float, y: Float) = (sqrt(((x-oXY).pow(2) + (y-oXY).pow(2)).toDouble()) < radius)

    // 转换
    private fun pixleToDp(pixel: Int, density: Int) = pixel/density
    private fun DpToPixle(dp: Int, density: Int) = dp*density


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 获取系统
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)

        // 屏幕长宽的最小值，因为要画圆
        val atMostSize = min(displayMetrics.widthPixels, displayMetrics.heightPixels)
        // 默认0.8倍
        val defaultWidth = (0.8 * atMostSize).toInt()

        val width = getSize(widthMeasureSpec, defaultWidth, atMostSize)
        val height = getSize(heightMeasureSpec, defaultWidth, atMostSize)

        this.size = max(width, height)
        // 需要剪去线宽，不然超出布局会不显示
        this.radius = ((size-2*strokeWidth) / 2).toFloat()
        this.oXY = (size / 2).toFloat()

        setMeasuredDimension(size, size)
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

        // 背景圆
        val paint = Paint().also {
            it.isAntiAlias = true
            it.color = circleColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = strokeWidth.toFloat()
        }
        canvas?.drawCircle(oXY, oXY, radius, paint)

        // 圆弧所在的rect
        val oval = RectF().also {
            it.left = width/2 - radius
            it.right = width - it.left
            it.top = height/2 - radius
            it.bottom = height - it.top
        }
        paint.color = arcColor
        canvas?.drawArc(oval, -90f, 360*scale, false, paint)

        // 时间
        paint.style = Paint.Style.FILL
        paint.textSize = radius/2
        paint.textAlign = Paint.Align.CENTER
        val distance = paint.fontMetrics.let{ (it.bottom - it.top)/2 - it.bottom}
        // x,y 是text底线中间坐标，不是整体中间
        val min = String.format("%02d", time/60)
        val sec = String.format("%02d", time%60)
        canvas?.drawText("$min:$sec", oXY, oXY+distance, paint)
    }


    fun start(): Boolean {
        // 在paused和stoped下可start
        if ((status == STATUS.RUNNING) || (time == 0L))  return false


        val initScale = if (countDownTime != 0L) (countDownTime-time).toFloat()/countDownTime.toFloat() else 0f
        valueAnimator = ValueAnimator.ofFloat(initScale, 1f).apply {
            duration = time*1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                scale = it.animatedValue as Float
                invalidate()
            }
        }
        valueAnimator?.start()

        countDownTimer = object: CountDownTimer(time*1000, 1000L) {
            override fun onFinish() {
                // 因为view可能被销毁后，此timer还在运行，然后调用下面报错
                this@PomodoroView?.let {
                    it.stop()
                    it.onFinishListener?.onClick(it)
                    it.time = it.countDownTime
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                time -= 1
            }
        }
        countDownTimer?.start()

        status = STATUS.RUNNING

        return true
    }


    fun stop(): Boolean {
        // 在paused和stoped下可stop
        if (status == STATUS.STOPED) return false

        valueAnimator?.cancel()
        countDownTimer?.cancel()

        time = 0L
        scale = 0F
        status = STATUS.STOPED

        return true
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isStarted)  return true

        when (event?.actionMasked) {
            ACTION_DOWN -> {
                if ((status == STATUS.STOPED) && isContained(event.x, event.y))
                    this.callOnClick()
            }
        }

        return true
    }


    enum class STATUS {
        RUNNING, STOPED
    }

}


