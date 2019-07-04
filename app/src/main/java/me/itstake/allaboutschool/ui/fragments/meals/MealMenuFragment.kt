package me.itstake.allaboutschool.ui.fragments.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.meal_menu_fragment.*
import me.itstake.allaboutschool.R
import me.itstake.allaboutschool.SharedViewModel
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.ui.adapters.MealMenuRecyclerAdapter


class MealMenuFragment : Fragment() {

    companion object {

        const val ARG_MEAL = "meal"
        fun newInstance(meal: Meal): MealMenuFragment {
            val fragment = MealMenuFragment()
            val args = Bundle()
            args.putString(ARG_MEAL, Gson().toJson(meal))
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MealsViewModel
    private lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        if(arguments != null) {
            meal = Gson().fromJson(arguments?.getString(ARG_MEAL), Meal::class.java)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MealsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.meal_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //autohide bottomnav on scroll
        scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            sharedViewModel.bottomNavIsShow.value = scrollY - oldScrollY > 0
        }
        var informationExists = false
        if(meal.breakfast.isNotEmpty()) {
            informationExists = true
            breakfast.visibility = View.VISIBLE
            breakfast_recycler.visibility = View.VISIBLE
            breakfast_recycler.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = MealMenuRecyclerAdapter(meal.breakfast)
            }
        }
        if(meal.lunch.isNotEmpty()) {
            informationExists = true
            lunch.visibility = View.VISIBLE
            lunch_recycler.visibility = View.VISIBLE
            lunch_recycler.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = MealMenuRecyclerAdapter(meal.lunch)
            }
        }
        if(meal.dinner.isNotEmpty()) {
            informationExists = true
            dinner.visibility = View.VISIBLE
            dinner_recycler.visibility = View.VISIBLE
            dinner_recycler.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = MealMenuRecyclerAdapter(meal.dinner)
            }
        }
        if(meal.breakfast.isNotEmpty() && meal.lunch.isNotEmpty() && meal.dinner.isNotEmpty()) {
            divider1.visibility = View.VISIBLE
            divider2.visibility = View.VISIBLE
        } else if(meal.breakfast.isNotEmpty() && meal.lunch.isNotEmpty()) {
            divider1.visibility = View.VISIBLE
        } else if(meal.lunch.isNotEmpty() && meal.dinner.isNotEmpty()) {
            divider2.visibility = View.VISIBLE
        }
        if(!informationExists) {
            no_results.visibility = View.VISIBLE
        }
    }

}
