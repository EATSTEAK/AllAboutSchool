package me.itstake.allaboutschool.ui.fragments.settings.data

import android.content.Context
import android.view.View
import me.itstake.allaboutschool.data.settings.SettingEnums

interface SettingData<T> {

    enum class SettingPage(val key:Int) {
        MAIN(0),
        GENERAL(1),
        FEED(2),
        TO_DO(3),
        TIME_TABLE(4),
        MEALS(5);

        companion object {
            private val values = values()
            fun getByKey(key: Int) = values.first {
                it.key == key
            }
        }
    }
    val settingEnum: SettingEnums
    var settingValue: T
    val iconId: Int?
    val localizedTitle: String
    val localizedDetails: String

    fun initView(view: View)

    fun changeValue(context: Context, value: T)
}