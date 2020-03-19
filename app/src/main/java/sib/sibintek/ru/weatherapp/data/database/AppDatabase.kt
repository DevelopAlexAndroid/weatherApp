package sib.sibintek.ru.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel

@Database(entities = [WeatherModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}