package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager

class BooleanSettingData(override val settingEnum: SettingEnums, override var settingValue: Boolean, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String): SettingData<Boolean> {

    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        if(localizedDetails == "") view.findViewById<TextView>(R.id.setting_entry_details).visibility = View.GONE else view.findViewById<TextView>(R.id.setting_entry_details).text = localizedDetails
        view.findViewById<Switch>(R.id.setting_entry_switch).visibility = View.VISIBLE
        view.findViewById<Switch>(R.id.setting_entry_switch).isChecked = settingValue
        if(iconId != null) view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId) else view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        view.setOnClickListener { v ->
            this.changeValue(v.context, !settingValue)
            view.findViewById<Switch>(R.id.setting_entry_switch).isChecked = settingValue
        }
    }

    override fun changeValue(context: Context, value: Boolean) {
        settingValue = value
        SettingsManager(context).putSettings(settingEnum, value)
    }
}