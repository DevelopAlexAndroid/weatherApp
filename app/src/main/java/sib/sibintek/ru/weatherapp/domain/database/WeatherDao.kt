package sib.sibintek.ru.weatherapp.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import sib.sibintek.ru.weatherapp.domain.data.view.WeatherModel

@Dao
interface WeatherDao  {

    @Query("SELECT * FROM WeatherModel")
    fun getWeather(): Single<WeatherModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherModel: WeatherModel): Long

}