package me.itstake.allaboutschool.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import me.itstake.neisinfo.School
import org.json.JSONException
import org.json.JSONObject

class SettingsManager(val context: Context?) {
    private val pref:SharedPreferences? = context?.getSharedPreferences("settings", 0)
    private val editor:SharedPreferences.Editor? = pref?.edit()

    fun getSettings(settingEnums: SettingEnums): Any? = when {
            settingEnums.type == Int::class -> pref?.getInt(settingEnums.key, settingEnums.defaultValue as Int)
            settingEnums.type == String::class -> pref?.getString(settingEnums.key, settingEnums.defaultValue as String)
            settingEnums.type == Boolean::class -> pref?.getBoolean(settingEnums.key, settingEnums.defaultValue as Boolean)
            settingEnums.type == School::class -> pref?.getSchool(settingEnums.key)
            settingEnums.type == IntArray::class -> Gson().fromJson(pref?.getString(settingEnums.key, settingEnums.defaultValue as String), IntArray::class.java)
            else -> null
    }

    fun putSettings(settingEnums: SettingEnums, value: Any) {
        if(value::class == settingEnums.type) {
            when {
                settingEnums.type == Int::class -> editor?.putInt(settingEnums.key, value as Int)
                settingEnums.type == String::class -> editor?.putString(settingEnums.key, value as String)
                settingEnums.type == Boolean::class -> editor?.putBoolean(settingEnums.key, value as Boolean)
                settingEnums.type == School::class -> editor?.putSchool(settingEnums.key, value as School)
                settingEnums.type == IntArray::class -> editor?.putString(settingEnums.key, Gson().toJson(value as IntArray))
            }
            editor?.commit()
        }
    }
}

fun SharedPreferences.getSchool(key: String): School? {
    try {
        val schoolJson = JSONObject(getString(key, ""))
        if (schoolJson.has("code") && schoolJson.has("region") && schoolJson.has("type") && schoolJson.has("name")) {
            return School(School.SchoolType.valueOf(schoolJson["type"] as String), School.SchoolRegion.valueOf(schoolJson["region"] as String), schoolJson["code"] as String, schoolJson["name"] as String)
        }
    } catch(e: JSONException) {
        return null
    }
    return null
}

fun SharedPreferences.Editor.putSchool(key: String, school: School) {
    val schoolJson = JSONObject()
    schoolJson.put("code", school.code)
    schoolJson.put("region", school.region.name)
    schoolJson.put("type", school.type.name)
    schoolJson.put("name", school.name)
    putString(key, schoolJson.toString())
}