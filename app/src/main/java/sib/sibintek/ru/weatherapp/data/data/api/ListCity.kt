package sib.sibintek.ru.weatherapp.data.data.api

import com.google.gson.annotations.SerializedName

class ListCity {
    @SerializedName("data")
    var items = ArrayList<City>()
}