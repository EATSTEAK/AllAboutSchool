package me.itstake.allaboutschool.ui.fragments.settings

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
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

    private lateinit var sharedViewModel: SharedViewModel
    val args: SettingsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingsManager = SettingsManager(activity?.applicationContext)
        val settingEntries = ArrayList<SettingData<out Any?>>()

        //toolbar custom color
        settings_toolbar.setBackgroundColor(Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String))
        (activity as AppCompatActivity).setSupportActionBar(settings_toolbar)
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val color = Color.parseColor(it)
            val colorAni = ValueAnimator.ofObject(ArgbEvaluator(), (settings_toolbar.background as ColorDrawable).color, color)
            colorAni.duration = 250
            colorAni.addUpdateListener {ani ->
                settings_toolbar.setBackgroundColor(ani.animatedValue as Int)
            }
            colorAni.start()
        })

        //nav backbutton operation
        settings_toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
        //Detect page level via navArgs
        if(args.settingsType == SettingData.SettingPage.MAIN.key) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
            settingEntries.add(SettingNavigationData(SettingEnums.NAVIGATION, 1, R.drawable.ic_settings_grey600_24dp, getString(R.string.settings_general_title), getString(R.string.settings_general_details)))
        } else {
            val page = SettingData.SettingPage.getByKey(args.settingsType)
            settings_toolbar.setTitle(when(page) {
                SettingData.SettingPage.GENERAL -> R.string.settings_general_title
                else -> R.string.frag_settings
            })
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
            SettingEnums.values().forEach {
                if(it.name.startsWith(page.name) && it.showUI) {
                    val localizedTitle = if(it.localizedTitle == null) "" else getString(it.localizedTitle)
                    val localizedDetails = if(it.localizedDetails == null) "" else getString(it.localizedDetails)
                    val data = when(it.dataType) {
                        BooleanSettingData::class -> BooleanSettingData(it, settingsManager.getSettings(it) as Boolean, it.iconId, localizedTitle, localizedDetails)
                        ColorSettingData::class -> ColorSettingData(it, settingsManager.getSettings(it) as String, it.iconId, localizedTitle, localizedDetails)
                        SchoolFindSettingData::class -> SchoolFindSettingData(it, settingsManager.getSchool(), it.iconId, localizedTitle, localizedDetails)
                        SelectionSettingData::class -> if(it.selectionList != null) SelectionSettingData(it, settingsManager.getSettings(it) as Int, it.iconId, localizedTitle, localizedDetails, it.selectionList) else null
                        else -> null
                    }
                    if(data != null) settingEntries.add(data)
                }
            }

        }

        //recycler init
        settings_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = SettingAdapter(settingEntries)
        }
    }

}
