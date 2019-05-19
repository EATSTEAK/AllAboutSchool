package me.itstake.allaboutschool.data.meals

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Meal::class), version = 1)
abstract class MealsDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}