package me.itstake.allaboutschool.ui.fragments.settings.data

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager

class SelectionSettingData(override val settingEnum: SettingEnums, override var settingValue: Int, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String, val settingList: Int): SettingData<Int> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        view.findViewById<TextView>(R.id.setting_entry_details).text = view.resources.getStringArray(settingList)[settingValue]
        if(iconId != null) {
            view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId)
            val sharedViewModel = (view.context as FragmentActivity).run {
                ViewModelProviders.of(this).get(SharedViewModel::class.java)
            }
            view.findViewById<ImageView>(R.id.setting_entry_icon).setColorFilter(Color.parseColor(sharedViewModel.primaryColor.value))
        } else {
            view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        }
        view.setOnClickListener { v ->
            AlertDialog.Builder(v.context).setTitle(localizedTitle).setItems(settingList) { _, which ->
                changeValue(v.context, which)
                view.findViewById<TextView>(R.id.setting_entry_details).text = view.resources.getStringArray(settingList)[which]
                if(settingEnum == SettingEnums.GENERAL_THEME) {
                    Toast.makeText(v.context, R.string.applies_restart, Toast.LENGTH_LONG).show()
                    //(view.context as AppCompatActivity).recreate()
                }
            }.show()
        }
    }

    override fun changeValue(context: Context, value: Int) {
        settingValue = value
        SettingsManager(context).putSettings(settingEnum, value)
    }
}