package sib.sibintek.ru.weatherapp.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import sib.sibintek.ru.weatherapp.di.ActivityScope
import sib.sibintek.ru.weatherapp.domain.ApiWeather
import sib.sibintek.ru.weatherapp.domain.database.AppDatabase
import sib.sibintek.ru.weatherapp.domain.database.WeatherDao
import sib.sibintek.ru.weatherapp.domain.repository.WeatherRepository
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherContract
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherPresenter
import javax.inject.Singleton

@Module
abstract class WeatherModule {

    @ActivityScope
    @Binds
    abstract fun providesPresenter(weatherPresenter: WeatherPresenter): WeatherContract.Presenter

    @Module
    companion object {
        @Singleton
        @Provides
        fun providesRepository(apiWeather: ApiWeather, appDatabase: AppDatabase): WeatherRepository =
            WeatherRepository(apiWeather, appDatabase)
    }
}