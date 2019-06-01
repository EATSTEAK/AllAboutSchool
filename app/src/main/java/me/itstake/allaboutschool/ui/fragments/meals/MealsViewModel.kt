package me.itstake.allaboutschool.ui.fragments.meals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.itstake.allaboutschool.data.meals.Meal
import java.util.*

class MealsViewModel: ViewModel() {
    val selectedDay = MutableLiveData<Date>()
    val weekData = MutableLiveData<List<Meal>>()
}