package sib.sibintek.ru.weatherapp.tools

import android.content.Context
import android.content.SharedPreferences
import sib.sibintek.ru.weatherapp.tools.Const.FIRST_START
import javax.inject.Inject

class UiTolls @Inject constructor(context: Context) {

    private var preferences: SharedPreferences

    init {
        val preferences: SharedPreferences? = context.getSharedPreferences("COMMON", 0)
        this.preferences = preferences!!
    }

    /**SharedPreferences*/
    fun saveValueString(KEY: String, text: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(KEY, text)
        editor.apply()
    }

    fun getValueString(KEY: String): String? {
        return preferences.getString(KEY, "")
    }

    fun saveValueInt(KEY: String, value: Int) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(KEY, value)
        editor.apply()
    }

    fun getValueInt(KEY: String): Int {
        return preferences.getInt(KEY, FIRST_START)
    }

    fun saveValueLong(KEY: String, value: Long) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong(KEY, value)
        editor.apply()
    }

    fun getValueLong(KEY: String): Long {
        return preferences.getLong(KEY, FIRST_START.toLong())
    }

}