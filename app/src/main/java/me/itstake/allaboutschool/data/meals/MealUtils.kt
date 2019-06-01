package me.itstake.allaboutschool.data.meals

import android.content.Context
import me.itstake.allaboutschool.data.NeisDatabase
import me.itstake.allaboutschool.data.settings.SettingsManager
import me.itstake.neisinfo.School
import java.io.IOException
import java.util.*

class MealUtils {
    companion object {
        fun getWeekData(context: Context, date: Date, forceUpdate: Boolean): List<Meal> {
            val dateList = ArrayList<Date>()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+9"))
            calendar.time = date
            calendar.firstDayOfWeek = Calendar.MONDAY
            for(i in 1..7) {
                calendar.set(Calendar.DAY_OF_WEEK, i)
                dateList.add(calendar.time)
            }
            var mealData: List<Meal> = ArrayList()
            if(!forceUpdate) {
                val neisDatabase = NeisDatabase.getInstance(context) ?: throw IOException("Cannot connect to database.")
                mealData = neisDatabase.mealDao().getByDays(dateList)
            }
            if(mealData.isEmpty()) {
                val school = SettingsManager(context).getSchool() ?: throw NullPointerException("Cannot get stored school information.")
                cacheMeals(context, school, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
                val neisDatabase = NeisDatabase.getInstance(context) ?: throw IOException("Cannot connect to database.")
                mealData = neisDatabase.mealDao().getByDays(dateList)
            }
            return mealData
        }

        private fun cacheMeals(context: Context, school: School, year: Int, month: Int) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+9"))
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            val mealList = school.getMealMonthly(year, month)
            val conv = ArrayList<Meal>()
            mealList.keys.forEach {
                calendar.set(Calendar.DAY_OF_MONTH , it)
                conv.add(Meal(day = calendar.time, breakfast = mealList[it]?.breakfast, lunch = mealList[it]?.lunch, dinner = mealList[it]?.dinner))
            }
            NeisDatabase.getInstance(context)?.mealDao()?.insertMeals(*conv.toTypedArray())
        }
    }
}