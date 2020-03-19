package sib.sibintek.ru.weatherapp.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import sib.sibintek.ru.weatherapp.data.data.api.ApiWeatherModel

/**
 * Ключ аккаунта
 *  " &appid=b6907d289e10d714a6e88b30761fae22 "
 * */

interface ApiWeather {

    @GET("weather")
    fun getWeatherByid(
        @Query("id") idCity: Int,
        @Query("appid") key: String
    ): Single<ApiWeatherModel>

    @GET("weather")
    fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String
    ): Single<ApiWeatherModel>

}