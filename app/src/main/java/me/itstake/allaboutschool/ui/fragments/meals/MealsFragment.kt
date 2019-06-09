package me.itstake.allaboutschool.ui.fragments.meals

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.meal_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.data.meals.MealUtils
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.allaboutschool.ui.adapters.MealsPagerAdapter
import java.text.SimpleDateFormat
import java.util.*


class MealsFragment : Fragment() {

    companion object {
        fun newInstance() = MealsFragment()
    }

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MealsViewModel
    private val handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if(msg != null) {
                when(msg.what) {
                    0 -> {
                        viewModel.weekData.value = msg.obj as List<Meal>
                    }
                    1 -> AlertDialog.Builder(requireActivity()).setMessage(R.string.wrong_school_value).setTitle(R.string.error).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MealsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        val view = inflater.inflate(R.layout.meal_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(meals_toolbar)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingsManager = SettingsManager(activity?.applicationContext)
        //initiate toolbar action buttons
        meals_toolbar.inflateMenu(R.menu.meal_menu)
        meals_toolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.day_select -> {
                    //Toast.makeText(context, "Hello World!", Toast.LENGTH_LONG).show()
                    val cal = Calendar.getInstance()
                    cal.time = viewModel.selectedDay.value ?: Date(System.currentTimeMillis())
                    DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        cal.set(year, month, dayOfMonth)
                        viewModel.selectedDay.value = cal.time
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
                }
            }
            true
        }
        //for dynamic color change
        val primaryColor = Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String)
        val secondaryColor = Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_SECONDARY_COLOR) as String)
        meals_toolbar.setBackgroundColor(primaryColor)
        meals_tab.setBackgroundColor(primaryColor)
        meals_tab.setSelectedTabIndicatorColor(secondaryColor)
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val color = Color.parseColor(it)
            val colorAni = ValueAnimator.ofObject(ArgbEvaluator(), (meals_toolbar.background as ColorDrawable).color, color)
            colorAni.duration = 250
            colorAni.addUpdateListener {ani ->
                if(meals_toolbar != null && meals_tab != null) {
                    meals_toolbar.setBackgroundColor(ani.animatedValue as Int)
                    meals_tab.setBackgroundColor(ani.animatedValue as Int)
                }
            }
            colorAni.start()
        })
        sharedViewModel.secondaryColor.observe(this, Observer<String>{
            meals_tab.setSelectedTabIndicatorColor(Color.parseColor(it))
        })

        //loading viewpager setup
        meals_viewpager.adapter = MealsPagerAdapter(requireFragmentManager(), arrayListOf())
        meals_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(meals_tab))
        meals_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //DO NOTHING
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //DO NOTHING
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                meals_viewpager.setCurrentItem(meals_tab.selectedTabPosition, true)
            }
        })

        //ui update as meals data received.
        viewModel.weekData.observe(this, Observer<List<Meal>>{
            val sdf = SimpleDateFormat("M/d (E)", Locale.getDefault())
            meals_tab.removeAllTabs()
            var todayIndex = 0
            var finalTodayIndex = 0
            it.forEach {meal ->
                meals_tab.addTab(meals_tab.newTab().setText(sdf.format(meal.day)))
                if(sdf.format(meal.day) != sdf.format(Date(System.currentTimeMillis()))) todayIndex++ else finalTodayIndex = todayIndex
            }
            meals_tab.getTabAt(finalTodayIndex)?.select()
            if(meals_tab.height == 0 && it.isNotEmpty()) {
                meals_tab.expand()
            }
            if(meals_viewpager.adapter is MealsPagerAdapter) {
                meals_viewpager.adapter = MealsPagerAdapter(requireFragmentManager(), it)
                meals_viewpager.setCurrentItem(meals_tab.selectedTabPosition, false)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        //data setup
        if(viewModel.selectedDay.value == null) viewModel.selectedDay.value = Date(System.currentTimeMillis())
        viewModel.selectedDay.observe(this, Observer {
            Thread(Runnable {
                try {
                    val message = Message()
                    message.what = 0
                    message.obj = MealUtils.getWeekData(requireContext(), it, false)
                    handler.sendMessage(message)
                } catch(e: NullPointerException) {
                    handler.sendEmptyMessage(1)
                }
            }).start()
        })
    }

}

private fun View.expand() {
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    this.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    layoutParams.height = 1
    visibility = View.VISIBLE
    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            layoutParams.height = if (interpolatedTime == 1f)
                ViewGroup.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    // 0.1dp/ms
    a.duration = ((targetHeight * 10 / context.resources.displayMetrics.density).toInt()).toLong()
    startAnimation(a)
}
