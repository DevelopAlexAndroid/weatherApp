package sib.sibintek.ru.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import sib.sibintek.ru.weatherapp.data.data.api.ListCity

@Dao
interface CityDao {
    @Query("SELECT * FROM ListCity")
    fun getCity(): Single<ListCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: ListCity)
}