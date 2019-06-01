package me.itstake.allaboutschool.data.meals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.itstake.neisinfo.MealMenu
import java.util.*

@Entity(tableName = "meals")
data class Meal(
        @PrimaryKey val day: Date,
        @ColumnInfo(name="breakfast") val breakfast: List<MealMenu>?,
        @ColumnInfo(name="lunch") val lunch: List<MealMenu>?,
        @ColumnInfo(name="dinner") val dinner: List<MealMenu>?
)