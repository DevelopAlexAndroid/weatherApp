package sib.sibintek.ru.weatherapp.domain.repository

import io.reactivex.Single
import sib.sibintek.ru.weatherapp.domain.ApiWeather
import sib.sibintek.ru.weatherapp.domain.data.api.ApiWeatherModel
import sib.sibintek.ru.weatherapp.domain.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.domain.database.AppDatabase
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

}