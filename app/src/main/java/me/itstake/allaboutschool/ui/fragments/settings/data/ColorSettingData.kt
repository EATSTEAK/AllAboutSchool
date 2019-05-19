package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.settings.SettingEnums

class ColorSettingData(override val settingEnum: SettingEnums, override var settingValue: String, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String): SettingData<String> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        if(localizedDetails == "") view.findViewById<TextView>(R.id.setting_entry_details).visibility = View.GONE else view.findViewById<TextView>(R.id.setting_entry_details).text = localizedDetails
        if(iconId != null) view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId) else view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        view.setOnClickListener { v ->
            println("Clicked")
        }
    }

    override fun changeValue(context: Context, value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}