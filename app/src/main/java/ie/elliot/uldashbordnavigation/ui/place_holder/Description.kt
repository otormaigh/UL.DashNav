package ie.elliot.uldashbordnavigation.ui.place_holder

import android.content.Context
import android.util.AttributeSet
import ie.elliot.uldashbordnavigation.R

/**
 * Place holder view for a 'Description'
 *
 * @author Elliot Tormey
 * @since 11/02/2017
 */
class Description(context: Context, attributeSet: AttributeSet?)
    : CircleBar(context, attributeSet, R.color.background_description, R.dimen.height_description) {
    var widthWeight: Int = 1

    init {
        if (attributeSet != null) {
            val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.Description, 0, 0)
            try {
                widthWeight = typedArray.getInt(R.styleable.Description_width_weight, 1)
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth: Int
        if (widthWeight != 1) {
            minWidth = widthMeasureSpec / widthWeight
        } else {
            minWidth = widthMeasureSpec
        }

        setMeasuredDimension(minWidth, heightMeasureSpec)
        super.onMeasure(minWidth, heightMeasureSpec)
    }
}