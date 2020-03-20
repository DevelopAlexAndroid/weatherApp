package sib.sibintek.ru.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sib.sibintek.ru.weatherapp.data.data.api.ListCity
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel

@Database(entities = [WeatherModel::class, ListCity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun citiesDao(): CityDao
}