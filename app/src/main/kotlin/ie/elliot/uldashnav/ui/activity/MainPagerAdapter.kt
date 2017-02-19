package ie.elliot.uldashnav.ui.activity

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.elliot.uldashnav.R
import ie.elliot.uldashnav.ui.view.ListHeader

/**
 * @author Elliot Tormey
 * @since 12/02/2017
 */
class MainPagerAdapter(private val itemCount: Int,
                       private val listHeader: ListHeader) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.view_pager_main, container, false) as ViewGroup
        container.addView(layout)

        val recyclerView = (layout.findViewById(R.id.recyclerView) as RecyclerView)

        recyclerView.adapter = MainAdapter()
        recyclerView.layoutManager = LinearLayoutManager(layout.context)
        listHeader.setRecyclerView(recyclerView)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any?) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun getCount(): Int = itemCount
}