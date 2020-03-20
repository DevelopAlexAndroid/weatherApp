package sib.sibintek.ru.weatherapp.tools

import sib.sibintek.ru.weatherapp.data.data.api.ApiWeatherModel
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.tools.Const.CALL_SUCCESS

class ConverterWeather {

    fun convertApiWeather(apiWeatherModel: ApiWeatherModel): WeatherModel {
        val weatherModel = WeatherModel()
        weatherModel.cod = apiWeatherModel.cod
        if (weatherModel.cod == CALL_SUCCESS) {
            weatherModel.description = apiWeatherModel.weather?.get(0)?.description
            weatherModel.speedWind = apiWeatherModel.wind?.speed
            weatherModel.humidity = apiWeatherModel.main?.humidity
            weatherModel.pressure = apiWeatherModel.main?.pressure
            weatherModel.temp = calculationKelvinToCelsius(apiWeatherModel.main?.temp!!) //привести к цельсию
            weatherModel.icon = apiWeatherModel.weather?.get(0)?.icon
            weatherModel.name = apiWeatherModel.name
        }
        return weatherModel
    }

    fun calculationFahrenheit(valueCelsius: Double): Double {
        return (valueCelsius * 9/5 + 32)
    }

    fun calculationCelsius(valueFahrenheit: Double): Double {
        return valueFahrenheit.minus(32) * 5/9
    }

    private fun calculationKelvinToCelsius(valueKelvin: Double): Double {
        return valueKelvin.minus(273.15)
    }

}