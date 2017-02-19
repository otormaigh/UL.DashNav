package ie.elliot.uldashnav.ViewExt

import android.view.View

/**
 * @author Elliot Tormey
 * @since 19/02/2017
 */

fun View.changeBounds(isHeight: Boolean, changeBy: Float, minVal: Float, maxVal: Float, currentVal: Int) {
    // Only allow height change
    // IF : currentVal is above minimum AND below maximum.
    // OR : currentVal is below OR is minimum and changeBy is increasing height
    // OR : currentVal is above OR is maximum and changeBy is decreasing height
    if ((currentVal > minVal && currentVal < maxVal)
            || (currentVal <= minVal && changeBy < 0)
            || currentVal >= maxVal && changeBy > 0) {
        var newVal: Int = (currentVal - (changeBy / 3)).toInt()
        // If newVal is below minVal, reset.
        if (newVal < minVal) {
            newVal = minVal.toInt()
        }

        if (isHeight) {
            layoutParams.height = newVal
        } else {
            layoutParams.width = newVal
        }
    }
}