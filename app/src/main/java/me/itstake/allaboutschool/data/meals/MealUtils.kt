package me.itstake.allaboutschool.data.meals

import android.content.Context
import me.itstake.allaboutschool.data.AppDatabase
import me.itstake.allaboutschool.data.settings.SettingEnums
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.neisinfo.School
import java.io.IOException
import java.util.*

class MealUtils {
    companion object {
        fun getWeekData(context: Context, date: Date, forceUpdate: Boolean): List<Meal> {
            val dateList = ArrayList<Date>()
            val monthList = ArrayList<Int>()
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.firstDayOfWeek = Calendar.MONDAY
            for(i in 0..6) {
                calendar.set(Calendar.DAY_OF_WEEK, i)
                if(!monthList.contains(calendar.get(Calendar.MONTH))) monthList.add(calendar.get(Calendar.MONTH))
                dateList.add(calendar.time)
            }
            var mealData: List<Meal> = ArrayList()
            if(!forceUpdate) {
                val neisDatabase = AppDatabase.getInstance(context) ?: throw IOException("Cannot connect to database.")
                mealData = neisDatabase.mealDao().getByDays(dateList)
            }
            if(mealData.isEmpty()) {
                val school = SettingsManager(context).getSettings(SettingEnums.GENERAL_SCHOOL_INFO) as School? ?: throw NullPointerException("Cannot get stored school information.")
                monthList.forEach { cacheMeals(context, school, calendar.get(Calendar.YEAR), it) }
                val neisDatabase = AppDatabase.getInstance(context) ?: throw IOException("Cannot connect to database.")
                mealData = neisDatabase.mealDao().getByDays(dateList)
            }
            return mealData
        }

        private fun cacheMeals(context: Context, school: School, year: Int, month: Int) {
            println("Requested Year and Month: $year / ${month+1}")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            val mealList = school.getMealMonthly(year, month+1)
            val conv = ArrayList<Meal>()
            mealList.keys.forEach {
                calendar.set(Calendar.DAY_OF_MONTH , it)
                conv.add(Meal(day = calendar.time, breakfast = mealList[it]?.breakfast ?:arrayListOf(), lunch = mealList[it]?.lunch ?:arrayListOf(), dinner = mealList[it]?.dinner ?:arrayListOf()))
            }
            AppDatabase.getInstance(context)?.mealDao()?.insertMeals(*conv.toTypedArray())
        }
    }
}