package me.itstake.allaboutschool.ui.fragments.meals

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.meal_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import java.util.*

class MealsFragment : Fragment() {

    companion object {
        fun newInstance() = MealsFragment()
    }

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MealsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MealsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.meal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingsManager = SettingsManager(activity?.applicationContext)
        val primaryColor = Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String)
        meals_toolbar.setBackgroundColor(primaryColor)
        meals_tab.setBackgroundColor(primaryColor)
        (activity as AppCompatActivity).setSupportActionBar(meals_toolbar)
        viewModel.selectedDay.value = Date(System.currentTimeMillis())
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val color = Color.parseColor(it)
            meals_toolbar.setBackgroundColor(color)
            meals_tab.setBackgroundColor(color)
        })
    }

}
