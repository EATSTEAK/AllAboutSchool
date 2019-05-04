package me.itstake.allaboutschool.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_card.view.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.feed.FeedData

class FeedAdapter(private val dataset: Array<FeedData>): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.default_card, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.view.cardTitle.text = dataset[position].title
        holder.view.cardDetails.text = dataset[position].details
    }

    override fun getItemCount(): Int = dataset.size






}