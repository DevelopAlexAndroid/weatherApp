package sib.sibintek.ru.weatherapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sib.sibintek.ru.weatherapp.di.ActivityScope
import sib.sibintek.ru.weatherapp.ui.activity.weather.WeatherActivity
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment

@Module
abstract class AppModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [WeatherModule::class])
    abstract fun contributeWeatherActivity(): WeatherActivity
}