package me.itstake.allaboutschool.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_card.view.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.feed.FeedData

class FeedAdapter(private val dataset: Array<FeedData>): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(val cardView: CardView): RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.default_card, parent, false) as CardView
        return FeedViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.cardView.cardTitle.text = dataset[position].title
        holder.cardView.cardDetails.text = dataset[position].details
    }

    override fun getItemCount(): Int = dataset.size






}