package me.itstake.allaboutschool.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.data.meals.MealDao

@Database(entities = arrayOf(Meal::class), version = 1)
@TypeConverters(DateConverter::class, MealConverter::class)
abstract class NeisDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        private var INSTANCE:NeisDatabase? = null

        fun getInstance(context: Context): NeisDatabase? {
            if(INSTANCE == null) {
                synchronized(NeisDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NeisDatabase::class.java, "neis.db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}