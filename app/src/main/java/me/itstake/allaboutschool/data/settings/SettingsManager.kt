package me.itstake.allaboutschool.data.settings

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(val context: Context) {
    val pref:SharedPreferences = context.getSharedPreferences("settings", 0)
    val editor:SharedPreferences.Editor = pref.edit()

    fun getSettings(settingEnums: SettingEnums): Any? = when {
            settingEnums.type == Int::class -> pref.getInt(settingEnums.key, 0)
            settingEnums.type == String::class -> pref.getString(settingEnums.key, "")
            settingEnums.type == Boolean::class -> pref.getBoolean(settingEnums.key, false)
            else -> null
    }

    fun putSettings(settingEnums: SettingEnums, value: Any) {
        if(value::class == settingEnums.type) {
            when {
                settingEnums.type == Int::class -> editor.putInt(settingEnums.key, 0 as Int)
                settingEnums.type == String::class -> editor.putString(settingEnums.key, value as String)
                settingEnums.type == Boolean::class -> editor.putBoolean(settingEnums.key, value as Boolean)
            }
        }
    }
}