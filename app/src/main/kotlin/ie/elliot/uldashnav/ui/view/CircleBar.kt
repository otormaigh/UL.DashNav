package ie.elliot.uldashnav.ui.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import ie.elliot.uldashnav.R

/**
 * @author Elliot Tormey
 * @since 11/02/2017
 */
open class CircleBar(context: Context,
                     attributeSet: AttributeSet?,
                     @ColorRes
                     defaultBackgroundRes: Int,
                     @DimenRes
                     private val radiusResId: Int) : View(context, attributeSet) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0, 0)

    private val backgroundPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val cornerRadius: Float by lazy { (height / 2).toFloat() }
    private var barWidth: Float = 0f
    private val widthAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
    var shouldAnimate: Boolean = true

    init {
        widthAnimator.duration = 700
        widthAnimator.interpolator = AccelerateDecelerateInterpolator()

        @ColorRes
        val backgroundColour: Int

        if (attributeSet != null) {
            val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.CircleBar, 0, 0)
            try {
                backgroundColour = typedArray.getColor(R.styleable.CircleBar_backgroundColour, defaultBackgroundRes)
            } finally {
                typedArray.recycle()
            }
        } else {
            backgroundColour = defaultBackgroundRes
        }

        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = ContextCompat.getColor(context, backgroundColour)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minHeight: Int
        // If height is supplied by the user, respect its value.
        // Else set height to default value.
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            minHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            minHeight = Math.round(resources.getDimension(radiusResId)) * 2
        }

        setMeasuredDimension(widthMeasureSpec, minHeight)
        setBarWidth(measuredWidth.toFloat(), shouldAnimate)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Left corner
        canvas.drawCircle(cornerRadius, cornerRadius, cornerRadius, backgroundPaint)
        // Right corner
        canvas.drawCircle(barWidth - cornerRadius, cornerRadius, cornerRadius, backgroundPaint)
        // In-between
        canvas.drawRect(RectF(cornerRadius, 0f, barWidth - cornerRadius, height.toFloat()), backgroundPaint)
    }

    protected fun setBarWidth(barWidth: Float, shouldAnimate: Boolean) {
        if (shouldAnimate) {
            setBarWidth(0f, false)
            widthAnimator.addUpdateListener({ animation ->
                val interpolation = animation.animatedValue as Float
                setBarWidth(interpolation * barWidth, false)
            })
            if (!widthAnimator.isStarted) {
                widthAnimator.start()
            }
        } else {
            this.barWidth = barWidth
            postInvalidate()
        }
    }
}