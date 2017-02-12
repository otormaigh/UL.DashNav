package ie.elliot.uldashbordnavigation.ui.place_holder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import ie.elliot.uldashbordnavigation.R

/**
 * @author Elliot Tormey
 * @since 12/02/2017
 */
class ListHeader(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private val headerPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var headerHeight: Int = 0
    private val minHeight = 180
    private val maxHeight: Float by lazy { resources.getDimension(R.dimen.height_list_header) }

    init {
        headerPaint.style = Paint.Style.FILL
        headerPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        headerHeight = measuredHeight
        setMeasuredDimension(widthMeasureSpec, headerHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(Rect(0, 0, measuredWidth, headerHeight), headerPaint)
    }

    fun changeHeightBy(changeBy: Int) {
        // Only allow height change
        // IF : Height is above minimum AND below maximum.
        // OR : Height is below OR is minimum and changeBy is increasing height
        // OR : Height is above OR is maximum and changeBy is decreasing height
        // TODO : Elliot -> Get actual value for minHeight
        if ((headerHeight > minHeight && headerHeight < maxHeight)
                || (headerHeight <= minHeight && changeBy < 0)
                || (headerHeight >= maxHeight && changeBy > 0)) {
            headerHeight -= (changeBy / 2)

            // If height is below minHeight, reset.
            if (headerHeight < minHeight) {
                headerHeight = minHeight
            }
            layoutParams.height = headerHeight

            // Only request layout if its not in the middle of one already.
            if (!isInLayout) {
                requestLayout()
            }
        }
    }
}