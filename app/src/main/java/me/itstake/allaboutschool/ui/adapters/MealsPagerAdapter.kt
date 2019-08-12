package me.itstake.allaboutschool.ui.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.ui.fragments.LoadingFragment
import me.itstake.allaboutschool.ui.fragments.meals.MealMenuFragment

class MealsPagerAdapter(fragmentManager: FragmentManager, private val dataset: List<Meal>, private val errorCode: Int): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if(dataset.isEmpty()) return LoadingFragment.newInstance(errorCode)
        return MealMenuFragment.newInstance(dataset[position])
    }

    override fun getCount(): Int = if(dataset.isEmpty()) 1 else dataset.size

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}