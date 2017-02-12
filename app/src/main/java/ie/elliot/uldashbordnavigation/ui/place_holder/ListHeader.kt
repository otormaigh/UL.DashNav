package ie.elliot.uldashbordnavigation.ui.place_holder

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
    private val headerRect by lazy { Rect(0, 0, measuredWidth, measuredHeight) }
    private val headerPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    init {
        headerPaint.style = Paint.Style.FILL
        headerPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(headerRect, headerPaint)
    }
}