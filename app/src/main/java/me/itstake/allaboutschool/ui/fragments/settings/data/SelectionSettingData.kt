package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.settings.SettingEnums

class SelectionSettingData(override val settingEnum: SettingEnums, override var settingValue: Int, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String, val settingList: Array<Int>): SettingData<Int> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        view.findViewById<TextView>(R.id.setting_entry_details).text = view.resources.getString(settingList[settingValue])
        if(iconId != null) view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId) else view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        view.setOnClickListener { v ->
            println("Clicked")
        }
    }

    override fun changeValue(context: Context, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}