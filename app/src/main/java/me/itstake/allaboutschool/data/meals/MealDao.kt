package me.itstake.allaboutschool.data.meals

import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface MealDao {

    @Query("SELECT * FROM meals")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meals WHERE day IN (:day)")
    fun getByDays(day: Calendar): Meal
}