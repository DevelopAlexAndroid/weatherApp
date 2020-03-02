package sib.sibintek.ru.weatherapp.domain.data.view

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherModel(@PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var cod: Int? = null,
    var name: String? = null,

    //Температура в цельсиях
    var temp: Int? = null,
    //Название погоды
    var description: String? = null,
    //Иконка погоды
    var icon: String? = null,

    //Скорость Ветра
    var speedWind: Double? = null,

    //давление
    var pressure: Double? = null,

    //Влажность
    var humidity: Int? = null)