package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager


class ColorSettingData(override val settingEnum: SettingEnums, override var settingValue: String, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String): SettingData<String> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        if(localizedDetails == "") view.findViewById<TextView>(R.id.setting_entry_details).visibility = View.GONE else view.findViewById<TextView>(R.id.setting_entry_details).text = localizedDetails
        if(iconId != null) view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId) else view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        view.findViewById<ImageView>(R.id.color_circle).setColorFilter(Color.parseColor(settingValue))
        view.findViewById<ImageView>(R.id.color_circle).visibility = View.VISIBLE
        view.setOnClickListener { v ->
            val dialog = ColorPickerDialog.newBuilder().setColor(Color.parseColor(settingValue)).create()

            dialog.setColorPickerDialogListener(object : ColorPickerDialogListener {
                override fun onDialogDismissed(dialogId: Int) {
                    // DO NOTHING
                }
                override fun onColorSelected(dialogId: Int, color: Int) {
                    changeValue(v.context, String.format("#%06X", 0xFFFFFF and color))
                    view.findViewById<ImageView>(R.id.color_circle).setColorFilter(color)
                }
            })
            dialog.show((v.context as FragmentActivity).supportFragmentManager, null)
        }
    }

    override fun changeValue(context: Context, value: String) {
        SettingsManager(context).putSettings(settingEnum, value)
        val model = ViewModelProviders.of(context as FragmentActivity).get(SharedViewModel::class.java)
        when(settingEnum) {
            SettingEnums.GENERAL_PRIMARY_COLOR -> model.primaryColor.value = value
            SettingEnums.GENERAL_SECONDARY_COLOR -> model.secondaryColor.value = value
        }
    }
}