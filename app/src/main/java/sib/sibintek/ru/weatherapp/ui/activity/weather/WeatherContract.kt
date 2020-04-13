package sib.sibintek.ru.weatherapp.ui.activity.weather

import sib.sibintek.ru.weatherapp.data.data.api.City
import sib.sibintek.ru.weatherapp.data.data.api.ListCity
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel

interface WeatherContract {

    interface View {
        fun setData(weatherModel: WeatherModel)
        fun showLoading()
        fun showError()
        fun showMessage(idRecourse: Int)
        fun showNewDegrees(value: Double)

        fun createLocationListenerAndGetLocal()
        fun startChoiceFragment(cities: List<City>)
        fun loadCities()
    }

    interface Presenter {
        fun onCreate()
        fun onRefreshed()
        fun onDestroy()

        fun clickSwitchTemp(value: Double, isFahrenheit: Boolean)
        fun clickSwitchCity()
        fun clickSwitchCity(idCity: Int)
        fun clickMyLocation()
        fun permissionDenied()
        fun newGeolocationData(lat: Double, lon: Double)

        fun citiesLoading(cities: ListCity)
    }

}