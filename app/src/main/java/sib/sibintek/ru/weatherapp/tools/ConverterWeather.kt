package sib.sibintek.ru.weatherapp.tools

import sib.sibintek.ru.weatherapp.domain.data.api.ApiWeatherModel
import sib.sibintek.ru.weatherapp.domain.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.tools.Const.CALL_SUCCES
import sib.sibintek.ru.weatherapp.tools.Const.FLAG_FAHRENHEIT

class ConverterWeather {

    fun convertApiWeather(apiWeatherModel: ApiWeatherModel): WeatherModel {
        val weatherModel = WeatherModel()
        weatherModel.cod = apiWeatherModel.cod
        if (weatherModel.cod == CALL_SUCCES) {
            weatherModel.description = apiWeatherModel.weather?.get(0)?.description
            weatherModel.speedWind = apiWeatherModel.wind?.speed
            weatherModel.humidity = apiWeatherModel.main?.humidity
            weatherModel.pressure = apiWeatherModel.main?.pressure
            weatherModel.temp = calculationKelvin(apiWeatherModel.main?.temp!!)
            weatherModel.icon = apiWeatherModel.weather?.get(0)?.icon
            weatherModel.name = apiWeatherModel.name
        }
        return weatherModel
    }

    fun calculationKelvin(valueKelvin: Double): Int{
        var temp = valueKelvin
        temp = if (FLAG_FAHRENHEIT) {
            (temp.minus(273.15)) * 9/5 + 32
        } else {
            temp.minus(273.15)
        }
        return temp.toInt()
    }

}