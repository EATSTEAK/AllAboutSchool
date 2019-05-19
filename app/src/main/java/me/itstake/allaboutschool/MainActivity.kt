package me.itstake.allaboutschool

import android.os.Bundle
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
    private lateinit var model: SharedViewModel
    /*private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { t ->
        when(t.itemId) {
            R.id.action_feed -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, FeedFragment.newInstance())
                        .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_time_table -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, TimeTableFragment.newInstance())
                        .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_todo -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ToDoFragment.newInstance())
                        .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_meals -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MealsFragment.newInstance())
                        .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_settings -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SettingsFragment.newInstance())
                        .commitNow()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        model.bottomNavIsShow.observe(this, Observer<Boolean> { b ->
            if(b) {
                nav.clearAnimation()
                nav.animate().translationY(nav.height.toFloat()).duration = 200
            } else {
                nav.clearAnimation()
                nav.animate().translationY(0f).duration = 200
            }
        })
        setContentView(R.layout.main_activity)
        navHost = main_nav_host_fragment as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.main_nav_graph)
        val settingsManager = SettingsManager(applicationContext)
        graph.startDestination = when(settingsManager.getSettings(SettingEnums.GENERAL_DEFAULT_FRAGMENT)) {
            1 -> R.id.action_time_table
            2 -> R.id.action_todo
            3 -> R.id.action_meals
            else -> R.id.action_feed
        }
        navHost.navController.graph = graph
        setupWithNavController(nav, navHost.navController)

        //nav.setOnNavigationItemSelectedListener(navListener)

    }

}
