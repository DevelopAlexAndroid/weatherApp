package sib.sibintek.ru.weatherapp.data.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import sib.sibintek.ru.weatherapp.data.database.Converters

@Entity
class ListCity(
    @PrimaryKey
    var id: Long = 1L,
    @SerializedName("data")
    @TypeConverters(Converters::class)
    var listCities: List<City>? = null
)