package me.itstake.allaboutschool.data.meals.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.itstake.neisinfo.MealMenu


@Entity(tableName="menu_review")
data class MenuReview(
    @PrimaryKey val menu: String,
    @ColumnInfo val allergy: List<MealMenu.AllergyInfo>,
    @ColumnInfo val review: Int
)
