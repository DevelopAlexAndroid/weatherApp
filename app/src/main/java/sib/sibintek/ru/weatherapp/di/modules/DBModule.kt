package sib.sibintek.ru.weatherapp.di.modules

import androidx.room.Room
import dagger.Module
import dagger.Provides
import sib.sibintek.ru.weatherapp.App
import sib.sibintek.ru.weatherapp.data.database.AppDatabase

@Module
 class DBModule {

    @Provides
     fun providesDB(app: App): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

}
