package sib.sibintek.ru.weatherapp.ui.activity.weather

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.data.data.view.WeatherModel
import sib.sibintek.ru.weatherapp.data.repository.WeatherRepository
import sib.sibintek.ru.weatherapp.tools.Const
import sib.sibintek.ru.weatherapp.tools.Const.CALL_SUCCES
import sib.sibintek.ru.weatherapp.tools.Const.FIRST_START
import sib.sibintek.ru.weatherapp.tools.Const.KEY_LOCATION_OR_CITY
import sib.sibintek.ru.weatherapp.tools.Const.KEY_TIME_LAST_CAll
import sib.sibintek.ru.weatherapp.tools.Const.KEY_USER_CHOICE_CITY
import sib.sibintek.ru.weatherapp.tools.Const.LOAD_CITY
import sib.sibintek.ru.weatherapp.tools.Const.LOAD_CITY_DEFAULT_KRASNODAR
import sib.sibintek.ru.weatherapp.tools.Const.LOAD_DATA_FROM_DATABASE
import sib.sibintek.ru.weatherapp.tools.Const.LOAD_GEOLOCATION
import sib.sibintek.ru.weatherapp.tools.Const.TIME_CORRECT_DATA
import sib.sibintek.ru.weatherapp.tools.ConverterWeather
import sib.sibintek.ru.weatherapp.tools.UiTolls
import javax.inject.Inject
import kotlin.concurrent.thread

class WeatherPresenter
@Inject
constructor(
    private var repository: WeatherRepository,
    private var converterWeather: ConverterWeather,
    private var uiTolls: UiTolls
) : WeatherContract.Presenter {

    private var viewState: WeatherContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(viewState: WeatherContract.View) {
        Log.d(Const.TAG_WEATHER, "Presenter.onCreate")
        this.viewState = viewState
        //Обновления погоды
        updateData()
    }

    override fun onRefreshed() {
        //Обнуляем последнее время запроса, так как пользователь принудительно обновляет экран
        uiTolls.saveValueLong(KEY_TIME_LAST_CAll, FIRST_START.toLong())
        updateData()
    }

    override fun onDestroy() {
        Log.d(Const.TAG_WEATHER, "Presenter.onDestroy")
        viewState = null
        compositeDisposable.clear()
    }

    override fun clickSwitchTemp(value: Double, isFahrenheit: Boolean) {
        Log.d(Const.TAG_WEATHER, "Presenter.clickSwitchButton")
        //Преобразования в кельвины, далее в цельсий <=> фаренгейты
        val temp = if (isFahrenheit) {
            converterWeather.calculationFahrenheit(value)
        } else {
            converterWeather.calculationCelsius(value)
        }

        viewState?.showNewDegrees(temp)
        Log.d(Const.TAG_WEATHER, "Presenter.clickSwitchButton  --  $temp")
    }

    override fun clickSwitchCity(idCity: Int) {
        Log.d(Const.TAG_WEATHER, "Presenter.clickSwitchCity")
        if (idCity != uiTolls.getValueInt(KEY_USER_CHOICE_CITY)) {
            //Обнуляем последнее время запроса, так как пользователь меняет точку сбора данных
            uiTolls.saveValueLong(KEY_TIME_LAST_CAll, FIRST_START.toLong())
            //Обновляем данные о выбранном городе
            uiTolls.saveValueInt(KEY_USER_CHOICE_CITY, idCity)
            choiceCity()
        } else {
            viewState?.showMessage(R.string.city_normal)
        }
    }

    override fun clickMyLocation() {
        Log.d(Const.TAG_WEATHER, "Presenter.clickMyLocation")
        if (uiTolls.getValueInt(KEY_LOCATION_OR_CITY) == LOAD_GEOLOCATION)
            viewState?.showMessage(R.string.local_normal)
        else {
            //Обнуляем последнее время запроса, так как пользователь меняет точку сбора данных
            uiTolls.saveValueLong(KEY_TIME_LAST_CAll, FIRST_START.toLong())
            choiceGeolocation()
        }
    }

    /**Вызывается в случае отсутствия доступа к местоположению пользователя
     * или выключенном сенсоре геолокации*/
    override fun permissionDenied() {
        Log.d(Const.TAG_WEATHER, "Presenter.permissionDenied")
        //Чтобы избежать частный случай с намеренным отключением доступа приложения к геолокации
        //обнуляем последнее время запроса
        uiTolls.saveValueLong(KEY_TIME_LAST_CAll, FIRST_START.toLong())
        //В таком случае надо отобразить данные по городу
        choiceCity()
    }

    override fun newGeolocationData(lat: Double, lon: Double) {
        //Проверка времени последней записи в БД, если больше 10 минут, то обновляем
        if (checkLastCallTime())
            getWeatherFromNetworkByLocation(lat, lon)
        else
            getWeatherFromDatabase()
    }

    private fun updateData() {
        /**Проверка флага обновления данных*/
        if (checkKeyUpdateData())
            choiceGeolocation()     //get data by geolocation
        else
            choiceCity()            //get data by city
    }

    private fun checkKeyUpdateData(): Boolean {
        val navigationLoad = uiTolls.getValueInt(KEY_LOCATION_OR_CITY)
        //Если первый запуск, то приоритет на загрузку по геолокации.
        return navigationLoad == FIRST_START || navigationLoad == LOAD_GEOLOCATION
        //Иначе navigationLoad = LOAD_CITY
    }

    private fun choiceGeolocation() {
        viewState?.showLoading()
        Log.d(Const.TAG_WEATHER, "Presenter.choiceGeolocation")
        viewState?.createLocationListenerAndGetLocal()
    }

    private fun choiceCity() {
        Log.d(Const.TAG_WEATHER, "Presenter.choiceCity")
        viewState?.showLoading()
        val idCity = checkUserChoiceCity()
        //Проверка времени последней записи в БД, если больше 10 минут, то обновляем
        if (checkLastCallTime())
            getWeatherFromNetworkById(idCity)
        else
            getWeatherFromDatabase()
    }

    //Проверка был ли выбран город пользователем, иначе загружаем Краснодар
    private fun checkUserChoiceCity(): Int {
        val userChoice = uiTolls.getValueInt(KEY_USER_CHOICE_CITY)
        return if (userChoice == FIRST_START) LOAD_CITY_DEFAULT_KRASNODAR else userChoice
    }

    private fun checkLastCallTime(): Boolean {
        val timeLastCall = uiTolls.getValueLong(KEY_TIME_LAST_CAll)
        return (timeLastCall == FIRST_START.toLong() ||
                ((timeLastCall + TIME_CORRECT_DATA) < System.currentTimeMillis()))
    }

    private fun getWeatherFromNetworkById(idCity: Int) {
        Log.d(Const.TAG_WEATHER, "Presenter.getWeatherFromNetworkById")
        compositeDisposable.add(repository.getWeatherFromNetworkById(idCity)
            .subscribeOn(Schedulers.io())
            .map { apiWeatherModel -> converterWeather.convertApiWeather(apiWeatherModel) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { res -> saveWeatherInDb(res) }
            .subscribe(
                { res -> setData(res, LOAD_CITY) },
                {
                    Log.d(
                        Const.TAG_WEATHER,
                        "Presenter.getWeatherFromNetworkById error ${it.message}"
                    )
                    viewState?.showError()
                }
            ))
    }

    private fun getWeatherFromNetworkByLocation(lat: Double, lon: Double) {
        Log.d(Const.TAG_WEATHER, "Presenter.getWeatherFromNetworkByLocation")
        compositeDisposable.add(repository.getWeatherFromNetworkByLocation(lat, lon)
            .subscribeOn(Schedulers.io())
            .map { apiWeatherModel -> converterWeather.convertApiWeather(apiWeatherModel) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { res -> saveWeatherInDb(res) }
            .subscribe(
                { res ->
                    uiTolls.saveValueInt(KEY_USER_CHOICE_CITY, FIRST_START)
                    setData(res, LOAD_GEOLOCATION)
                },
                {
                    Log.d(
                        Const.TAG_WEATHER,
                        "Presenter.getWeatherFromNetworkByLocation error ${it.message}"
                    )
                    viewState?.showError()
                }
            ))
    }

    private fun getWeatherFromDatabase() {
        Log.d(Const.TAG_WEATHER, "Presenter.getWeatherFromDatabase")
        compositeDisposable.add(repository.getWeatherFromDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> setData(res, LOAD_DATA_FROM_DATABASE) },
                {
                    Log.d(
                        Const.TAG_WEATHER,
                        "Presenter.getWeatherFromDatabase error ${it.message}"
                    )
                    viewState?.showError()
                }
            ))
    }

    private fun saveWeatherInDb(weatherModel: WeatherModel) {
        Log.d(Const.TAG_WEATHER, "Presenter.saveWeatherInDb")
        thread { repository.saveWeatherInDatabase(weatherModel) }
    }

    private fun setData(weatherModel: WeatherModel, flagLoad: Int) {
        if (weatherModel.cod == CALL_SUCCES) {
            viewState?.setData(weatherModel)
        } else
            viewState?.showError()

        if (flagLoad != LOAD_DATA_FROM_DATABASE) {
            //Устанавливаем последнее время запроса
            uiTolls.saveValueLong(KEY_TIME_LAST_CAll, System.currentTimeMillis())
            Log.d(Const.TAG_WEATHER, "Presenter.set_KEY_TIME_LAST_CAll")
            //Устанавливаем флаг для навигации при старте
            uiTolls.saveValueInt(KEY_LOCATION_OR_CITY, flagLoad)
            Log.d(Const.TAG_WEATHER, "Presenter.set_KEY_LOCATION_OR_CITY = $flagLoad")
        }
    }

}
