package me.itstake.allaboutschool.data

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.itstake.allaboutschool.data.meals.Meal
import me.itstake.allaboutschool.data.meals.MealDao
import me.itstake.allaboutschool.data.meals.menu.MenuReview
import me.itstake.allaboutschool.data.meals.menu.MenuReviewDao
import me.itstake.allaboutschool.ui.fragments.meals.MealsViewModel

@Database(entities = [Meal::class, MenuReview::class], version = 1)
@TypeConverters(DateConverter::class, MealConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun menuReviewDao(): MenuReviewDao

    companion object {
        private var INSTANCE:AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyAll(context: Context) {
            Thread(Runnable {
                getInstance(context)?.mealDao()?.deleteAll()
                getInstance(context)?.menuReviewDao()?.deleteAll()
            }).start()
            val mealsViewModel = context.run {
                ViewModelProviders.of(context as FragmentActivity).get(MealsViewModel::class.java)
            }
            mealsViewModel.weekData.value = ArrayList()
        }
    }
}