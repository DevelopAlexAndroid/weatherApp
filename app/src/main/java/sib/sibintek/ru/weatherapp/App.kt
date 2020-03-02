package sib.sibintek.ru.weatherapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import sib.sibintek.ru.weatherapp.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}