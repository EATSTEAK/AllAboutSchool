package me.itstake.allaboutschool.data.meals

import androidx.room.*
import java.util.*

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeals(vararg meals: Meal)

    @Delete
    fun deleteMeals(vararg meals:Meal)

    @Query("SELECT * FROM meals")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meals WHERE day IN (:days)")
    fun getByDays(days: List<Date>): List<Meal>

    @Query("DELETE FROM meals")
    fun deleteAll()

}