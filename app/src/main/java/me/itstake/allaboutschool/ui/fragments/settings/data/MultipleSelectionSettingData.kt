package me.itstake.allaboutschool.ui.fragments.settings.data

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager

class MultipleSelectionSettingData(override val settingEnum: SettingEnums, override var settingValue: List<Int>, override val iconId: Int?, override val localizedTitle: String, override val localizedDetails: String, val settingList: Int): SettingData<List<Int>> {
    override fun initView(view: View) {
        view.findViewById<TextView>(R.id.setting_entry_title).text = localizedTitle
        val builder = StringBuilder()
        val settingListObject = view.resources.getStringArray(settingList)
        settingValue.forEach { if(builder.isNotEmpty()) builder.append(", ${settingListObject[it]}") else builder.append(settingListObject[it]) }
        if(builder.isEmpty()) builder.append(view.resources.getString(R.string.none))
        view.findViewById<TextView>(R.id.setting_entry_details).text = builder.toString()
        if(iconId != null) {
            view.findViewById<ImageView>(R.id.setting_entry_icon).setImageResource(iconId)
            val sharedViewModel = (view.context as FragmentActivity).run {
                ViewModelProviders.of(this).get(SharedViewModel::class.java)
            }
            view.findViewById<ImageView>(R.id.setting_entry_icon).setColorFilter(Color.parseColor(sharedViewModel.primaryColor.value))
        } else {
            view.findViewById<ImageView>(R.id.setting_entry_icon).visibility = View.GONE
        }
        val checkedList = ArrayList(settingValue)
        val booleanArray = BooleanArray(settingListObject.size)
        settingValue.forEach { booleanArray[it] = true }
        view.setOnClickListener { v ->
            AlertDialog.Builder(v.context).setTitle(localizedTitle).setMultiChoiceItems(settingList, booleanArray) { dialog, which, checked ->
                if(checked) (if(!checkedList.contains(which)) checkedList.add(which)) else (if(checkedList.contains(which)) checkedList.remove(which))
            }.setPositiveButton(R.string.confirm) { _, _ ->
                        changeValue(view.context, checkedList)
                        builder.clear()
                        settingValue.forEach { if(builder.isNotEmpty()) builder.append(", ${settingListObject[it]}") else builder.append(settingListObject[it]) }
                        view.findViewById<TextView>(R.id.setting_entry_details).text = builder.toString()
                    }
                    .show()
        }
    }

    override fun changeValue(context: Context, value: List<Int>) {
        settingValue = value
        SettingsManager(context).putSettings(settingEnum, value.toIntArray())
    }
}