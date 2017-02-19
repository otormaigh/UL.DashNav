package ie.elliot.uldashnav.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import ie.elliot.uldashnav.R

/**
 * @author Elliot Tormey
 * @since 11/02/2017
 */
class Card(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private val backgroundPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val cornerRadius: Float by lazy { resources.getDimension(R.dimen.corner_radius) }
    private val cardRect: RectF by lazy { RectF(0f, 0f, width.toFloat(), height.toFloat()) }
    private val paddingDefaultHalf: Int by lazy { (resources.getDimension(R.dimen.padding_default_half)).toInt() }

    init {
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = ContextCompat.getColor(context, R.color.background_card)

        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.Card, 0, 0)
        val titleCount: Int
        val descriptionCount: Int
        try {
            titleCount = typedArray.getInt(R.styleable.Card_title_count, 0)
            descriptionCount = typedArray.getInt(R.styleable.Card_description_count, 0)
        } finally {
            typedArray.recycle()
        }

        val descriptionMargin: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        //descriptionMargin.width = (descriptionMargin.width * 0.5f).toInt()
        descriptionMargin.setMargins(0, paddingDefaultHalf, 0, 0)

        for (i in 0..titleCount - 1) {
            val title = CircleBar(context, attributeSet, R.color.background_title, R.dimen.radius_title)
            title.minimumWidth = (measuredWidth * 0.75f).toInt()

            addView(title)
        }
        for (i in 0..descriptionCount - 1) {
            val description = CircleBar(context, attributeSet, R.color.background_description, R.dimen.radius_description)
            description.layoutParams = descriptionMargin

            addView(description)
        }

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(cardRect, cornerRadius, cornerRadius, backgroundPaint)
    }
}