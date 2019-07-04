package me.itstake.allaboutschool.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.itstake.neisinfo.MealMenu

class MealConverter {

    @TypeConverter
    fun fromMealList(list: List<MealMenu>): String = Gson().toJson(list)

    @TypeConverter
    fun toMealList(json: String): List<MealMenu> {
        val listType = object: TypeToken<List<MealMenu>>() {}.type
        return Gson().fromJson(json, listType)
    }
}