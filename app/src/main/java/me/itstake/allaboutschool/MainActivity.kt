package me.itstake.allaboutschool

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager


class MainActivity : AppCompatActivity() {

    private lateinit var navHost:NavHostFragment
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsManager = SettingsManager(applicationContext)
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        //save theme values to viewmodel
        sharedViewModel.primaryColor.value = settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String
        sharedViewModel.secondaryColor.value = settingsManager.getSettings(SettingEnums.GENERAL_SECONDARY_COLOR) as String
        when(settingsManager.getSettings(SettingEnums.GENERAL_THEME) as Int) {
            0 -> setTheme(android.R.style.ThemeOverlay_Material_Light)
            1 -> setTheme(android.R.style.ThemeOverlay_Material_Dark)
        }

        //init view
        setContentView(R.layout.main_activity)

        //live color changes
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val window = window
            val color = Color.parseColor(it)
            val colorAni = ValueAnimator.ofObject(ArgbEvaluator(), (nav.background as ColorDrawable).color, color)
            colorAni.duration = 250
            colorAni.addUpdateListener {ani ->
                nav.setBackgroundColor(ani.animatedValue as Int)
                window.statusBarColor = ani.animatedValue as Int
            }
            colorAni.start()
        })

        //bottom nav init
        sharedViewModel.bottomNavIsShow.observe(this, Observer<Boolean> { b ->
            if(b) {
                nav.clearAnimation()
                nav.animate().translationY(nav.height.toFloat()).duration = 200
            } else {
                nav.clearAnimation()
                nav.animate().translationY(0f).duration = 200
            }
        })
        nav.setBackgroundColor(Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String))

        //generate dynamic nav graph
        navHost = main_nav_host_fragment as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.main_nav_graph)
        val array = resources.getStringArray(R.array.fragment_options)
        graph.startDestination = when(array[settingsManager.getSettings(SettingEnums.GENERAL_DEFAULT_FRAGMENT) as Int]) {
            resources.getString(R.string.frag_time_table) -> R.id.action_time_table
            resources.getString(R.string.frag_todo) -> R.id.action_todo
            resources.getString(R.string.frag_meals) -> R.id.action_meals
            else -> R.id.action_feed
        }
        navHost.navController.graph = graph
        setupWithNavController(nav, navHost.navController)
    }

}
