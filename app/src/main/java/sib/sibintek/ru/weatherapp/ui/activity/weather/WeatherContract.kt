package sib.sibintek.ru.weatherapp.ui.activity.weather

import sib.sibintek.ru.weatherapp.domain.data.view.WeatherModel

interface WeatherContract {

    interface View {
        fun setData(weatherModel: WeatherModel)
        fun showLoading()
        fun showError()
        fun showMessage(idRecourse: Int)
        fun showNewDegrees(value: Double)

        fun createLocationListenerAndGetLocal()
    }

    interface Presenter {
        fun onCreate(viewState: View)
        fun onRefreshed()
        fun onDestroy()

        fun clickSwitchTemp(value: Double, isFahrenheit: Boolean)
        fun clickSwitchCity(idCity: Int)
        fun clickMyLocation()
        fun permissionDenied()
        fun newGeolocationData(lat: Double, lon: Double)
    }

}