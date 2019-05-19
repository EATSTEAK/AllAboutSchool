package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.navigation.findNavController
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.ui.fragments.settings.SettingsFragmentDirections

class SettingNavigationData(override val settingEnum: SettingEnums, override var settingValue: Int, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String): SettingData<Int> {

    override fun changeValue(context: Context, value: Int) {
        //DO NOTHING
    }

    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        if(localizedDetails == "") view.findViewById<TextView>(R.id.setting_entry_details).visibility = View.GONE else view.findViewById<TextView>(R.id.setting_entry_details).text = localizedDetails
        view.findViewById<Switch>(R.id.setting_entry_switch).visibility = View.GONE
        if(iconId != null) view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId) else view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        view.setOnClickListener {
            if(settingValue != 0) it.findNavController().navigate(SettingsFragmentDirections.actionActionSettingsToSettingsLv1(settingValue)) else it.findNavController().navigateUp()
        }
    }

}