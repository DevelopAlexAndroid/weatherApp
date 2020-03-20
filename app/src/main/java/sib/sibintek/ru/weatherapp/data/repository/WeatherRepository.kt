package sib.sibintek.ru.weatherapp.data.repository

import io.reactivex.Single
import sib.sibintek.ru.weatherapp.data.ApiWeather
import sib.sibintek.ru.weatherapp.data.data.api.ApiWeatherModel
import sib.sibintek.ru.weatherapp.data.data.api.ListCity
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.data.database.AppDatabase
import sib.sibintek.ru.weatherapp.tools.Const.KEY_API
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(
    private var apiWeather: ApiWeather?,
    private var appDatabase: AppDatabase
) {

    //network
    fun getWeatherFromNetworkById(idCity: Int): Single<ApiWeatherModel> {
        return apiWeather!!.getWeatherByid(idCity, KEY_API)
    }

    fun getWeatherFromNetworkByLocation(lat: Double, lon: Double): Single<ApiWeatherModel> {
        return apiWeather!!.getWeatherByLocation(lat, lon, KEY_API)
    }

    //database
    fun getWeatherFromDatabase(): Single<WeatherModel> {
        return appDatabase.weatherDao().getWeather()
    }

    fun saveWeatherInDatabase(weatherModel: WeatherModel) {
        appDatabase.weatherDao().insert(weatherModel)
    }

    fun getCityFromDatabase(): Single<ListCity> {
        return appDatabase.citiesDao().getCity()
    }

    fun addCityInDatabase(listCities: ListCity) {
        appDatabase.citiesDao().insertCities(listCities)
    }

}