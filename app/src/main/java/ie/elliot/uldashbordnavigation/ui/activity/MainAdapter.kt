package ie.elliot.uldashbordnavigation.ui.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.elliot.uldashbordnavigation.R

/**
 * @author Elliot Tormey
 * @since 11/02/2017
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun getItemCount(): Int = 42

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            setIsRecyclable(false)
        }
    }
}