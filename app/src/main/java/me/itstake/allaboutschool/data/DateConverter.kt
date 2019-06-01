package me.itstake.allaboutschool.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDateString(value: String?): Date? = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(value)

    @TypeConverter
    fun dateToDateString(value: Date?): String? = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(value)
}