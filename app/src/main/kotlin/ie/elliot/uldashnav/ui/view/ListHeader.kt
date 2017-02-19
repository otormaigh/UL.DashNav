package ie.elliot.uldashnav.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v4.widget.Space
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import ie.elliot.uldashnav.R

/**
 * @author Elliot Tormey
 * @since 12/02/2017
 */
class ListHeader(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private val headerPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var headerHeight: Int = 0
    private val minHeight: Float
    private val maxHeight: Float by lazy { resources.getDimension(R.dimen.height_list_header) }
    private val titleParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    private val titleMinWidth by lazy { context.resources.getDimension(R.dimen.min_width_list_title) }
    private val titleMaxWidth by lazy { context.resources.getDimension(R.dimen.width_list_title) }

    // Views
    private val title by lazy { CircleBar(context, attributeSet, R.color.background_title, R.dimen.radius_title) }

    init {
        val androidTypedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        minHeight = context.resources.getDimension(androidTypedArray.getResourceId(0, 0))
        androidTypedArray.recycle()

        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL

        headerPaint.style = Paint.Style.FILL
        headerPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        title.layoutParams = titleParams
        title.shouldAnimate = false
        addView(title, resources.getDimension(R.dimen.width_list_title).toInt(), WRAP_CONTENT)

        val spaceParams = LinearLayout.LayoutParams(0, 1, 1f)
        val space = Space(context)
        space.layoutParams = spaceParams
        addView(space)

        val pageIndicator = PageIndicator(context)
        pageIndicator.indicatorCount = 5
        addView(pageIndicator)

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

    fun setRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                changeViewSize(title, false, dy, titleMinWidth, titleMaxWidth, title.layoutParams.width)
                changeViewSize(this@ListHeader, true, dy, minHeight, maxHeight, layoutParams.height)

                // Only request layout if its not in the middle of one already.
                if (!isInLayout) {
                    requestLayout()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun changeViewSize(view: View, isHeight: Boolean, changeBy: Int, minVal: Float, maxVal: Float, currentVal: Int) {
        // Only allow height change
        // IF : currentVal is above minimum AND below maximum.
        // OR : currentVal is below OR is minimum and changeBy is increasing height
        // OR : currentVal is above OR is maximum and changeBy is decreasing height
        if ((currentVal > minVal && currentVal < maxVal)
                || (currentVal <= minVal && changeBy < 0)
                || currentVal >= maxVal && changeBy > 0) {
            var newVal: Int = currentVal - (changeBy / 3)
            // If newVal is below minVal, reset.
            if (newVal < minVal) {
                newVal = minVal.toInt()
            }

            if (isHeight) {
                view.layoutParams.height = newVal
            } else {
                view.layoutParams.width = newVal
            }
        }
    }
}