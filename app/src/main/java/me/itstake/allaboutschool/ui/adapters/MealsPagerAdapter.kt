package me.itstake.allaboutschool.ui.adapters

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import me.itstake.allaboutschool.ui.fragments.meals.MealsViewModel

class MealsPagerAdapter(val model: MealsViewModel): PagerAdapter() {

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
}