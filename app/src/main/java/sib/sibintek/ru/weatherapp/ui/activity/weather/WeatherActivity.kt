package sib.sibintek.ru.weatherapp.ui.activity.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.data.data.api.City
import sib.sibintek.ru.weatherapp.data.data.api.ListCity
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.provides.GeolocationStatus
import sib.sibintek.ru.weatherapp.provides.LocationProviders
import sib.sibintek.ru.weatherapp.provides.LocationProviders.Companion.PERMISSION_ID
import sib.sibintek.ru.weatherapp.tools.AssetsParser
import sib.sibintek.ru.weatherapp.tools.Const.TAG_WEATHER
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment.Companion.TAG_CHOICE_CITY
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.CityHolder
import javax.inject.Inject
import kotlin.concurrent.thread

class WeatherActivity : DaggerAppCompatActivity(),
    WeatherContract.View,
    GeolocationStatus,
    CityHolder.CallbackItemClick {

    @Inject
    lateinit var presenter: WeatherContract.Presenter

    private var fragment = ChoiceCityFragment(this)
    private var locationProviders = LocationProviders()
    private val assetsParser: AssetsParser by lazy { AssetsParser() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        presenter.onCreate()
        addListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                locationProviders.getLastLocation(this)
            else
                presenter.permissionDenied()
        }
    }

    override fun setData(weatherModel: WeatherModel) {
        Log.d(TAG_WEATHER, "Activity.setData")

        weather_name.text = weatherModel.description
        city_toolbar.text = weatherModel.name
        text_container_wind.setData(weatherModel.speedWind.toString())
        text_container_pressure.setData(weatherModel.pressure.toString())
        text_container_humidity.setData(weatherModel.humidity.toString())
        text_container_rain.setData("2 %") // нет данных с сервера
        //В приоритете цельсий поэтому устанавливаем на switch на цельсий
        toggle.check(R.id.switch_C)
        switchClickable(false)
        temp.text = weatherModel.temp.toString().substringBefore(".")

        image_weather.setImageResource(getImage(weatherModel.icon!!))
        visibleUi(View.VISIBLE, View.INVISIBLE, View.GONE)
    }

    override fun showLoading() {
        Log.d(TAG_WEATHER, "Activity.showLoading")
        visibleUi(View.GONE, View.VISIBLE, View.GONE)
    }

    override fun showError() {
        Log.d(TAG_WEATHER, "Activity.showError")
        visibleUi(View.GONE, View.INVISIBLE, View.VISIBLE)
    }

    override fun showMessage(idRecourse: Int) {
        Toast.makeText(this, this.getString(idRecourse), Toast.LENGTH_LONG).show()
    }

    override fun showNewDegrees(value: Double) {
        temp.text = value.toString().substringBefore(".")
    }

    override fun createLocationListenerAndGetLocal() {
        locationProviders.constructor(this, LocationServices.getFusedLocationProviderClient(this))
        locationProviders.getLastLocation(this)
    }

    //Callback запроса новой геолокации
    override fun completeRequest(lat: Double, lon: Double) {
        presenter.newGeolocationData(lat, lon)
    }

    //Callback выбора города
    override fun onItemClicked(idCity: Int) {
        Log.d(TAG_WEATHER, "WeatherActivity.onItemClicked idCity = $idCity ")
        presenter.clickSwitchCity(idCity)
        fragment.dismiss()
    }

    override fun startChoiceFragment(cities: List<City>) {
        if (!fragment.isAdded) {
            fragment.addedListCities(cities as ArrayList<City>)
            fragment.show(supportFragmentManager, TAG_CHOICE_CITY)
        }
    }

    override fun loadCities() {
        thread {
            Log.d(TAG_WEATHER, "WeatherActivity.loadCities - start")
            val listModels = ListCity()
            val model = Gson().fromJson(assetsParser.loadJSONFromAssets(this), ListCity::class.java)
            listModels.listCities = ArrayList()
            //Только Русские города
            model.listCities?.forEach {
                if (it.country == "RU")
                    (listModels.listCities as ArrayList<City>).add(it)
            }
            presenter.citiesLoading(listModels)
            Log.d(TAG_WEATHER, "WeatherActivity.loadCities - finish")
        }
    }

    private fun addListener() {
        switch_city.setOnClickListener { presenter.clickSwitchCity() }
        geolocation.setOnClickListener { presenter.clickMyLocation() }
        switch_C.setOnClickListener {
            switchClickable(false)
            presenter.clickSwitchTemp(temp.text.toString().toDouble(), false)
        }
        switch_F.setOnClickListener {
            switchClickable(true)
            presenter.clickSwitchTemp(temp.text.toString().toDouble(), true)
        }
        switchClickable(false)
        swipe_refresh.setOnRefreshListener {
            Log.d(TAG_WEATHER, "WeatherActivity.SwipeToRefresh ")
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

    private fun switchClickable(isFahrenheit: Boolean) {
        switch_F.isClickable = !isFahrenheit
        switch_C.isClickable = isFahrenheit
    }

    private fun getImage(key: String): Int {
        return when (key) {
            "01n" -> R.drawable.sun
            "03n" -> R.drawable.cloud
            else -> R.drawable.def
        }
    }

}
