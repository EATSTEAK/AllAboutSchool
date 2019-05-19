package me.itstake.allaboutschool.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.settings_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.allaboutschool.ui.adapters.SettingAdapter
import me.itstake.allaboutschool.ui.fragments.settings.data.*

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SharedViewModel
    val args: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingsManager = SettingsManager(activity?.applicationContext)
        val settingEntries = ArrayList<SettingData<out Any>>()
        if(args.settingsType == SettingData.SettingPage.MAIN.key) {
            settingEntries.add(SettingNavigationData(SettingEnums.NAVIGATION, 1, R.drawable.ic_settings_grey600_24dp, getString(R.string.settings_general_title), getString(R.string.settings_general_details)))
        } else {
            SettingEnums.values().forEach {
                if(it.name.startsWith(SettingData.SettingPage.getByKey(args.settingsType).name) && it.showUI) {
                    val localizedTitle = if(it.localizedTitle == null) "" else getString(it.localizedTitle)
                    val localizedDetails = if(it.localizedDetails == null) "" else getString(it.localizedDetails)
                    val data = when(it.dataType) {
                        BooleanSettingData::class -> BooleanSettingData(it, settingsManager.getSettings(it) as Boolean, it.iconId, localizedTitle, localizedDetails)
                        ColorSettingData::class -> ColorSettingData(it, settingsManager.getSettings(it) as String, it.iconId, localizedTitle, localizedDetails)
                        SchoolFindSettingData::class -> SchoolFindSettingData(it, settingsManager.getSettings(it) as String, it.iconId, localizedTitle, localizedDetails)
                        SelectionSettingData::class -> if(it.selectionList != null) SelectionSettingData(it, settingsManager.getSettings(it) as Int, it.iconId, localizedTitle, localizedDetails, it.selectionList) else null
                        else -> null
                    }
                    if(data != null) settingEntries.add(data)
                }
            }
        }
        settings_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = SettingAdapter(settingEntries)

        }
    }

}
