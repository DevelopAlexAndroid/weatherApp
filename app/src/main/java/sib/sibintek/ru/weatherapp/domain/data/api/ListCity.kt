package sib.sibintek.ru.weatherapp.domain.data.api

import com.google.gson.annotations.SerializedName

class ListCity {
    @SerializedName("data")
    var items = ArrayList<City>()
}