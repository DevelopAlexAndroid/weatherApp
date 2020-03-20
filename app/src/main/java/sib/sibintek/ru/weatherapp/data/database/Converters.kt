package sib.sibintek.ru.weatherapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sib.sibintek.ru.weatherapp.data.data.api.City
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun listToJson(optionValues: List<City?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<City?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun jsonToList(optionValuesString: String?): List<City>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<City?>?>() {}.type
        return gson.fromJson<List<City>>(optionValuesString, type)
    }
}