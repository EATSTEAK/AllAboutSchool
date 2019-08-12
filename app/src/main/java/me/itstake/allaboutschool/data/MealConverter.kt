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

    @TypeConverter
    fun fromAllergyList(list: List<MealMenu.AllergyInfo>): String = Gson().toJson(list)

    @TypeConverter
    fun toAllergyList(json: String): List<MealMenu.AllergyInfo> {
        val listType = object: TypeToken<List<MealMenu.AllergyInfo>>() {}.type
        return Gson().fromJson(json, listType)
    }
}