package sib.sibintek.ru.weatherapp.di.modules

import dagger.Module
import dagger.Provides
import sib.sibintek.ru.weatherapp.App
import sib.sibintek.ru.weatherapp.tools.ConverterWeather
import sib.sibintek.ru.weatherapp.tools.UiTolls
import sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice.ChoiceCityFragment
import javax.inject.Singleton

@Module
class ToolsModule {

    @Singleton
    @Provides
    fun provideUI(app: App): UiTolls = UiTolls(app)

    @Singleton
    @Provides
    fun provideConverter(): ConverterWeather = ConverterWeather()

}
