package sib.sibintek.ru.weatherapp.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import sib.sibintek.ru.weatherapp.App
import sib.sibintek.ru.weatherapp.di.modules.ApiModule
import sib.sibintek.ru.weatherapp.di.modules.AppModule
import sib.sibintek.ru.weatherapp.di.modules.DBModule
import sib.sibintek.ru.weatherapp.di.modules.ToolsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ApiModule::class,
    DBModule::class,
    ToolsModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder
        fun build(): AppComponent
    }
}