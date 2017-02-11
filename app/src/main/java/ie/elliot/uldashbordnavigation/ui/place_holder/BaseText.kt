package ie.elliot.uldashbordnavigation.ui.place_holder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

/**
 * @author Elliot Tormey
 * @since 11/02/2017
 */
open class BaseText(context: Context,
                    attributeSet: AttributeSet?,
                    @ColorRes
                    defaultBackgroundRes: Int,
                    @DimenRes
                    private val defaultHeightRes: Int) : View(context, attributeSet) {

    private val backgroundPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val cornerRadius: Float by lazy { (height / 2).toFloat() }
    private val titleRect: RectF by lazy { RectF(cornerRadius, 0f, width.toFloat() - cornerRadius, height.toFloat()) }

    init {
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = ContextCompat.getColor(context, defaultBackgroundRes)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minWidth: Int = View.MeasureSpec.getSize(widthMeasureSpec)
        val minHeight: Int
        // If height is supplied by the user, respect its value.
        // Else set height to default value.
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            minHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            minHeight = Math.round(resources.getDimension(defaultHeightRes))
        }

        setMeasuredDimension(minWidth, minHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(cornerRadius, cornerRadius, cornerRadius, backgroundPaint)
        canvas.drawCircle(width - cornerRadius, cornerRadius, cornerRadius, backgroundPaint)
        canvas.drawRect(titleRect, backgroundPaint)
    }
}