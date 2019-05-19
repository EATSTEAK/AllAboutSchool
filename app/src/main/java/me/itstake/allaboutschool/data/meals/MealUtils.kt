package me.itstake.allaboutschool.data.meals

import android.content.Context
import androidx.room.Room
import me.itstake.neisinfo.School
import java.util.*

class MealUtils {
    companion object {
        private lateinit var db: MealsDatabase
        fun init(context: Context) {
            db = Room.databaseBuilder(
                    context,
                    MealsDatabase::class.java, "meals"
            ).build()
        }
        fun getWeekData(context: Context, date: Calendar) {
            val school = School(School.SchoolType.HIGH, School.SchoolRegion.BUSAN, "C100000357")
            if()
        }
    }
}