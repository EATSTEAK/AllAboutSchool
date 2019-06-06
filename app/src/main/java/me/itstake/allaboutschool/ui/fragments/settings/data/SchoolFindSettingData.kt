package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.NeisDatabase
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.allaboutschool.ui.dialogs.SchoolSearchDialog
import me.itstake.neisinfo.School

class SchoolFindSettingData(override val settingEnum: SettingEnums, override var settingValue: School?, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String): SettingData<School?> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        view.findViewById<TextView>(R.id.setting_entry_details).text = if(settingValue == null) view.context.resources.getText(R.string.no_school) else settingValue?.name
        if(iconId != null) {
            view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId)
            val sharedViewModel = (view.context as FragmentActivity).run {
                ViewModelProviders.of(this).get(SharedViewModel::class.java)
            }
            view.findViewById<ImageView>(R.id.setting_entry_icon).setColorFilter(Color.parseColor(sharedViewModel.primaryColor.value))
        } else {
            view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        }
        view.setOnClickListener {
            val dialog = SchoolSearchDialog(it.context)
            dialog.onSelectionListener = object: SchoolSearchDialog.OnSelectionListener {
                override fun onSelection(school: School) {
                    changeValue(it.context, school)
                    view.findViewById<TextView>(R.id.setting_entry_details).text = school.name
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }

    override fun changeValue(context: Context, value: School?) {
        settingValue = value
        if(value != null) SettingsManager(context).putSettings(settingEnum, value)
        if(settingEnum == SettingEnums.GENERAL_SCHOOL_INFO) {
            NeisDatabase.destroyAll(context)
        }
    }
}