package sib.sibintek.ru.weatherapp.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import sib.sibintek.ru.weatherapp.di.ActivityScope
import sib.sibintek.ru.weatherapp.data.ApiWeather
import sib.sibintek.ru.weatherapp.data.database.AppDatabase
import sib.sibintek.ru.weatherapp.data.repository.WeatherRepository
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherActivity
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherContract
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherPresenter
import javax.inject.Singleton

@Module
abstract class WeatherModule {

    @ActivityScope
    @Binds
    abstract fun bindsPresenter(weatherPresenter: WeatherPresenter): WeatherContract.Presenter

    @ActivityScope
    @Binds
    abstract fun bindsView(weatherActivity: WeatherActivity): WeatherContract.View

    @Module
    companion object {
        @Singleton
        @Provides
        fun providesRepository(apiWeather: ApiWeather, appDatabase: AppDatabase): WeatherRepository =
            WeatherRepository(apiWeather, appDatabase)
    }
}