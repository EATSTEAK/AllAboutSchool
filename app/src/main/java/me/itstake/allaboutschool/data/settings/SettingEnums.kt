package me.itstake.allaboutschool.data.settings

import android.content.res.Resources
import me.itstake.allaboutschool.MainActivity
import kotlin.reflect.KClass

enum class SettingEnums(val key:String, val type:KClass<*>, val showUI:Boolean, val defaultValue: Any) {

    GENERAL_PRIMARY_COLOR("general.primary_color", String::class, true, "#3F51B5"),
    GENERAL_SECONDARY_COLOR("general.accent_color", String::class, true, "#FF4081"),
    GENERAL_THEME("general.theme", Int::class, true, 0),
    GENERAL_DEFAULT_FRAGMENT("general.default_fragment", Int::class, true, 0),
    GENERAL_WELCOME_MANAGER("general.welcome_manager", Boolean::class, false, false),
    GENERAL_SCHOOL_CODE("general.school_code", String::class, true, ""),

    FEED_CLOCK("feed.clock", Boolean::class, true, true),
    FEED_CLOCK_24HRS("feed.clock_24hrs", Boolean::class, true, false),
    FEED_DATE("feed.date", Boolean::class, true, true),
    FEED_DATE_FORMAT("feed.date_format", String::class, true, "YYYY년 M월 D일"),
    FEED_DAILY_MESSAGE("feed.daily_message", Boolean::class, true, true),
    FEED_DAILY_MESSAGE_TYPE("feed.daily_message_type", Int::class, true, 0),
    FEED_INFO_WEATHER("feed.info_weather", Boolean::class, true, true),
    FEED_WEATHER_PROVIDER("feed.info_weather_provider", Int::class, true, 0),
    FEED_INFO_D_DAY("feed.info_d_day", Boolean::class, true, true),
    FEED_DISABLED_CARD("feed.disabled_card", String::class, true, "[]"),
    FEED_BACKGROUND("feed.background", String::class, true, 0),

    TIME_TABLE_DEFAULT_VIEW("time_table.default_view", Int::class, true, 0),
    TIME_TABLE_DAILY_TODO("time_table.daily_to_do", Boolean::class, true, true),

    TO_DO_SHOW_AS_D_DAY("to_do.show_as_d_day", Boolean::class, true, true),
    TO_DO_MAIN_LIST("to_do.main_list", Int::class, true, 0),

    MEALS_ALLERGY_WARNING("meals.allergy_warning", Boolean::class, true, true),

}