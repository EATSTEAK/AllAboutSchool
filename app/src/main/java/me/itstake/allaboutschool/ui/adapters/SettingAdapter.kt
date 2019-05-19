package me.itstake.allaboutschool.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.ui.fragments.settings.data.SettingData

class SettingAdapter(val dataset: ArrayList<SettingData<out Any>>): RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    class SettingViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.setting_entry, parent, false)
        return SettingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        dataset[position].initView(holder.view)
    }

    override fun getItemCount(): Int = dataset.size






}