package ie.elliot.uldashnav.ui.view

import android.content.Context
import android.util.AttributeSet
import ie.elliot.uldashnav.R

/**
 * Place holder view for a 'Title'
 *
 * @author Elliot Tormey
 * @since 11/02/2017
 */
class Title(context: Context, attributeSet: AttributeSet? = null)
    : CircleBar(context, attributeSet, R.color.background_title, R.dimen.height_title) {

    init {
        if (attributeSet != null) {
            val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.Title, 0, 0)
            try {
                shouldAnimate = typedArray.getBoolean(R.styleable.Title_should_animate, true)
            } finally {
                typedArray.recycle()
            }
        }
    }
}