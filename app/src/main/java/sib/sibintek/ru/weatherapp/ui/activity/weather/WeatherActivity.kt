package sib.sibintek.ru.weatherapp.ui.activity.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.domain.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.provides.GeolocationStatus
import sib.sibintek.ru.weatherapp.provides.LocationProviders
import sib.sibintek.ru.weatherapp.provides.LocationProviders.Companion.PERMISSION_ID
import sib.sibintek.ru.weatherapp.tools.Const
import sib.sibintek.ru.weatherapp.tools.Const.FLAG_FAHRENHEIT
import sib.sibintek.ru.weatherapp.tools.Const.FLAG_JSON_PARSING
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment.Companion.TAG_CHOICE_CITY
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.CityHolder
import javax.inject.Inject

class WeatherActivity : DaggerAppCompatActivity(),
    WeatherContract.View,
    GeolocationStatus,
    CityHolder.CallbackItemClick {

    @Inject
    lateinit var presenter: WeatherPresenter
    private var fragment = ChoiceCityFragment()

    private var locationProviders = LocationProviders()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        //TODO Рефакторинг парсинга и передачи данных
        fragment.parseCityJson(this)

        presenter.onCreate(this)
        addListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setData(weatherModel: WeatherModel) {
        Log.d(Const.TAG_WEATHER, "Activity.setData")

        temp.text = weatherModel.temp.toString()
        weather_name.text = weatherModel.description
        city_toolbar.text = weatherModel.name
        text_container_wind.setData(getString(R.string.wind), weatherModel.speedWind.toString())
        text_container_pressure.setData(getString(R.string.pressure), weatherModel.pressure.toString())
        text_container_humidity.setData(getString(R.string.humidity), weatherModel.humidity.toString())
        text_container_rain.setData(getString(R.string.rain), "2 %") // нет данных с сервера
        image_weather.setImageResource(getImage(weatherModel.icon!!))
        visibleUi(View.VISIBLE, View.INVISIBLE, View.GONE)
    }

    override fun showLoading() {
        Log.d(Const.TAG_WEATHER, "Activity.showLoading")
        visibleUi(View.GONE, View.VISIBLE, View.GONE)
    }

    override fun showError() {
        Log.d(Const.TAG_WEATHER, "Activity.showError")
        visibleUi(View.GONE, View.INVISIBLE, View.VISIBLE)
    }

    override fun showMessage(idRecourse: Int) {
        Toast.makeText(this, this.getString(idRecourse), Toast.LENGTH_LONG)
            .show()
    }

    override fun showNewDegrees(value: Int) {
        temp.text = value.toString()
    }

    override fun createLocationListenerAndGetLocal() {
        locationProviders.constructor(
            this,
            LocationServices.getFusedLocationProviderClient(this)
        )
        locationProviders.getLastLocation(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                locationProviders.getLastLocation(this)
            } else {
                presenter.permissionDenied()
            }
        }
    }

    //Callback запроса новой геолокации
    override fun completeRequest(lat: Double, lon: Double) {
        presenter.newGeolocationData(lat, lon)
    }

    //Callback выбора города
    override fun onItemClicked(idCity: Int) {
        Log.d(Const.TAG_WEATHER, "WeatherActivity.onItemClicked idCity = $idCity ")
        presenter.clickSwitchCity(idCity)
        fragment.dismiss()
    }

    private fun addListener() {
        switch_city.setOnClickListener {
            if (FLAG_JSON_PARSING) {
                fragment.addListener(this)
                fragment.show(supportFragmentManager, TAG_CHOICE_CITY)
            } else {
                showMessage(R.string.app_name)
            }
        }
        geolocation.setOnClickListener { presenter.clickMyLocation() }
        switch_C.setOnClickListener {
            if (FLAG_FAHRENHEIT) {
                FLAG_FAHRENHEIT = false
                presenter.clickSwitchTemp(temp.text.toString().toInt())
            }
        }
        switch_F.setOnClickListener {
            if (!FLAG_FAHRENHEIT) {
                FLAG_FAHRENHEIT = true
                presenter.clickSwitchTemp(temp.text.toString().toInt())
            }
        }
        swipe_refresh.setOnRefreshListener {
            Log.d(Const.TAG_WEATHER, "WeatherActivity.SwipeToRefresh ")
            Handler().postDelayed({ swipe_refresh.isRefreshing = false }, 1000)
            presenter.onRefreshed()
        }
    }

    private fun visibleUi(visibleUi: Int, visibleProgress: Int, visibleError: Int) {
        progress.visibility = visibleProgress

        error_message.visibility = visibleError

        weather_name.visibility = visibleUi
        temp.visibility = visibleUi
        image_weather.visibility = visibleUi
        text_container_wind.visibility = visibleUi
        text_container_pressure.visibility = visibleUi
        text_container_humidity.visibility = visibleUi
        text_container_rain.visibility = visibleUi
    }

    private fun getImage(key: String): Int {
        return when (key) {
            "01n" -> R.drawable.sun
            "03n" -> R.drawable.cloud
            else -> R.drawable.def
        }
    }

}
