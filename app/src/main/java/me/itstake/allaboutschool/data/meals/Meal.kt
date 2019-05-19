package me.itstake.allaboutschool.data.meals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.itstake.neisinfo.SchoolMeal
import java.util.*

@Entity
data class Meal(
    @PrimaryKey val day: Calendar,
    @ColumnInfo(name="breakfast") val breakfast: ArrayList<SchoolMeal>?,
    @ColumnInfo(name="lunch") val lunch: ArrayList<SchoolMeal>?,
    @ColumnInfo(name="dinner") val dinner: ArrayList<SchoolMeal>?
)