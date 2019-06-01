package me.itstake.allaboutschool.ui.adapters


import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import me.itstake.allaboutschool.ui.fragments.meals.MealsViewModel

class MealsPagerAdapter(fragmentManager: FragmentManager, pageCount:Int, val model: MealsViewModel): FragmentStatePagerAdapter(fragmentManager, pageCount) {

    override fun getItem(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
}