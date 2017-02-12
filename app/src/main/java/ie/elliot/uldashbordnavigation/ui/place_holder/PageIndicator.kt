package ie.elliot.uldashbordnavigation.ui.place_holder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ie.elliot.uldashbordnavigation.R

/**
 * @author Elliot Tormey
 * @since 12/02/2017
 */
class PageIndicator(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    private val indicatorPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val indicatorRadius: Float by lazy { resources.getDimension(R.dimen.radius_indicator) }
    private val indicatorCircumference: Float by lazy { indicatorRadius * 2 }
    private var widthRatio: Int = 1

    var indicatorCount: Int = 1

    init {
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
        val minWidth: Int = ((indicatorCircumference * 1.5f * indicatorCount) - indicatorRadius).toInt()
        // Height is : Indicator circumference.
        val minHeight: Int = indicatorCircumference.toInt()

        setMeasuredDimension(minWidth, minHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (position in 0..indicatorCount - 1) {
            canvas.drawCircle(getIndicatorStartX(position), indicatorRadius, indicatorRadius, indicatorPaint)
        }
    }

    private fun getIndicatorStartX(indicatorPosition: Int): Float {
        return (indicatorCircumference * 1.5f * indicatorPosition) + indicatorRadius
    }
}