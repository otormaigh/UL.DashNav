package ie.elliot.uldashnav.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import ie.elliot.uldashnav.R

/**
 * @author Elliot Tormey
 * @since 12/02/2017
 */
class PageIndicator(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet), ViewPager.OnPageChangeListener {
    private companion object {
        val LOG_TAG = "PageIndicator"
    }

    private val indicatorPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    //private val indicatorCircumference: Float by lazy { indicatorRadius * 2 }
    private var indicatorRadius: Float = 0f
    private var centerLeft: Float = 0f
    private val minRadius by lazy { context.resources.getDimension(R.dimen.min_radius_indicator) }
    private val maxRadius by lazy { context.resources.getDimension(R.dimen.max_radius_indicator) }

    var indicatorCount: Int = 1

    init {
        indicatorRadius = maxRadius
        indicatorPaint.style = Paint.Style.FILL
        indicatorPaint.color = ContextCompat.getColor(context, android.R.color.white)

        if (attributeSet != null) {
            val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.PageIndicator, 0, 0)
            try {
                indicatorCount = typedArray.getInt(R.styleable.PageIndicator_count, 1)
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Width is : (Indicator plus end padding) times (number of indicators) minus (padding of last indicator).
        //val minWidth: Int = ((indicatorCircumference * 1.5f * indicatorCount) - indicatorRadius).toInt()
        val minWidth = widthMeasureSpec
        // Height is : Indicator circumference.
        val minHeight: Int = (indicatorRadius * 2).toInt()

        setMeasuredDimension(minWidth, minHeight)

        centerLeft = measuredWidth / 2 - (((indicatorRadius * 2 * 1.5f * indicatorCount) - indicatorRadius) / 2)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (position in 0..indicatorCount - 1) {
            canvas.drawCircle(getIndicatorStartX(position), indicatorRadius, indicatorRadius, indicatorPaint)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        // Ignore
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Ignore
    }

    override fun onPageSelected(position: Int) {
        // Ignore
    }

    private fun getIndicatorStartX(indicatorPosition: Int): Float {
        return (indicatorRadius * 2 * 1.5f * indicatorPosition) + indicatorRadius + centerLeft
    }

    fun changeWidth(changeBy: Float) {
        if ((indicatorRadius > minRadius && indicatorRadius < maxRadius)
                || (indicatorRadius <= minRadius && changeBy < 0)
                || indicatorRadius >= maxRadius && changeBy > 0) {
            var newVal: Float = indicatorRadius - (changeBy / 10f)
            // If newVal is below minVal, reset.
            if (newVal < minRadius) {
                newVal = minRadius
            }
            indicatorRadius = newVal
        }
        if (!isInLayout) {
            requestLayout()
        }
    }
}