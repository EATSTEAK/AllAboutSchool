package me.itstake.allaboutschool.data.settings

import android.content.Context
import android.content.SharedPreferences
import me.itstake.neisinfo.School
import org.json.JSONObject

class SettingsManager(val context: Context?) {
    val pref:SharedPreferences? = context?.getSharedPreferences("settings", 0)
    val editor:SharedPreferences.Editor? = pref?.edit()

    fun getSettings(settingEnums: SettingEnums): Any? = when {
            settingEnums.type == Int::class -> pref?.getInt(settingEnums.key, settingEnums.defaultValue as Int)
            settingEnums.type == String::class -> pref?.getString(settingEnums.key, settingEnums.defaultValue as String)
            settingEnums.type == Boolean::class -> pref?.getBoolean(settingEnums.key, settingEnums.defaultValue as Boolean)
            else -> null
    }

    fun putSettings(settingEnums: SettingEnums, value: Any) {
        if(value::class == settingEnums.type) {
            when {
                settingEnums.type == Int::class -> editor?.putInt(settingEnums.key, value as Int)
                settingEnums.type == String::class -> editor?.putString(settingEnums.key, value as String)
                settingEnums.type == Boolean::class -> editor?.putBoolean(settingEnums.key, value as Boolean)
            }
            editor?.commit()
        }
    }

    fun getSchool(): School? {
        val schoolJson = JSONObject(getSettings(SettingEnums.GENERAL_SCHOOL_INFO) as String)
        if (schoolJson.has("code") && schoolJson.has("region") && schoolJson.has("type") && schoolJson.has("name")) {
            return School(School.SchoolType.valueOf(schoolJson["type"] as String), School.SchoolRegion.valueOf(schoolJson["region"] as String), schoolJson["code"] as String, schoolJson["name"] as String)
        }
        return null
    }

    fun setSchool(school: School) {
        val schoolJson = JSONObject()
        schoolJson.put("code", school.code)
        schoolJson.put("region", school.region.name)
        schoolJson.put("type", school.type.name)
        schoolJson.put("name", school.name)
        putSettings(SettingEnums.GENERAL_SCHOOL_INFO, schoolJson.toString(0))
    }
}