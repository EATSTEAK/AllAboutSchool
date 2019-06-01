package me.itstake.allaboutschool.ui.fragments.meals

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
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
import kotlinx.android.synthetic.main.meal_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.data.meals.MealUtils
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
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
        return inflater.inflate(R.layout.meal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val settingsManager = SettingsManager(activity?.applicationContext)
        val primaryColor = Color.parseColor(settingsManager.getSettings(SettingEnums.GENERAL_PRIMARY_COLOR) as String)
        meals_toolbar.setBackgroundColor(primaryColor)
        meals_tab.setBackgroundColor(primaryColor)
        (activity as AppCompatActivity).setSupportActionBar(meals_toolbar)
        sharedViewModel.primaryColor.observe(this, Observer<String>{
            val color = Color.parseColor(it)
            val colorAni = ValueAnimator.ofObject(ArgbEvaluator(), (meals_toolbar.background as ColorDrawable).color, color)
            colorAni.duration = 250
            colorAni.addUpdateListener {ani ->
                meals_toolbar.setBackgroundColor(ani.animatedValue as Int)
                meals_tab.setBackgroundColor(ani.animatedValue as Int)
            }
            colorAni.start()
        })


        //loading viewpager setup

        //ui update as meals data received.
        viewModel.weekData.observe(this, Observer<List<Meal>>{
            val sdf = SimpleDateFormat("M/d (E)", Locale.getDefault())
            meals_tab.removeAllTabs()
            it.forEach {meal ->
                meals_tab.addTab(meals_tab.newTab().setText(sdf.format(meal.day)))
            }
            if(meals_tab.height == 0 && it.isNotEmpty()) {
                println("TabLayout Expanded")
                meals_tab.expand()
            }
        })


        //data setup
        viewModel.selectedDay.value = Date(System.currentTimeMillis())
        Thread(Runnable {
            try {
                val message = Message()
                message.what = 0
                message.obj = MealUtils.getWeekData(requireContext(), Date(System.currentTimeMillis()), false)
                handler.sendMessage(message)
            } catch(e: NullPointerException) {
                handler.sendEmptyMessage(1)
            }
        }).start()
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
    // 1dp/ms
    a.duration = ((targetHeight / context.resources.displayMetrics.density).toInt()).toLong()
    startAnimation(a)
}
