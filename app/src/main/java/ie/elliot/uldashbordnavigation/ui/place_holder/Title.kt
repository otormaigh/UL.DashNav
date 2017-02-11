package ie.elliot.uldashbordnavigation.ui.place_holder

import android.content.Context
import android.util.AttributeSet
import ie.elliot.uldashbordnavigation.R

/**
 * Place holder view for a 'Title'
 *
 * @author Elliot Tormey
 * @since 11/02/2017
 */
class Title(context: Context, attributeSet: AttributeSet?)
    : BaseText(context, attributeSet, R.color.background_title, R.dimen.height_title) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(Math.round(widthMeasureSpec * 0.75f), heightMeasureSpec)
    }
}