package me.itstake.allaboutschool.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.ui.dialogs.SchoolSearchDialog
import me.itstake.neisinfo.School

class SchoolSearchRecyclerAdapter(private val dataset: List<School>, private val onSelectionListener: SchoolSearchDialog.OnSelectionListener): RecyclerView.Adapter<SchoolSearchRecyclerAdapter.SchoolSearchRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolSearchRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return SchoolSearchRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: SchoolSearchRecyclerViewHolder, position: Int) {
        holder.view.textView.text = dataset[position].name
        holder.view.setOnClickListener {
            onSelectionListener.onSelection(dataset[position])
        }
    }

    class SchoolSearchRecyclerViewHolder(val view: View): RecyclerView.ViewHolder(view)
}