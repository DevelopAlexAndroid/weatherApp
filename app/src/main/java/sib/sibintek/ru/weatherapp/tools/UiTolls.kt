package sib.sibintek.ru.weatherapp.tools

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.android.synthetic.main.activity_weather.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.tools.Const.FIRST_START
import java.io.IOException
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

    fun loadJSONFromAssets(activity: Activity): String? {
        var json: String? = null
        try {
            val inputStream = activity.assets.open("city_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Log.d(Const.TAG_WEATHER, "UiTolls.loadJSONFromAssets - fail")
            e.printStackTrace()
        }

        return json
    }

    fun getImage(key: String): Int {
        return when (key) {
            "01n" -> R.drawable.sun
            "03n" -> R.drawable.cloud
            else -> R.drawable.def
        }
    }

}