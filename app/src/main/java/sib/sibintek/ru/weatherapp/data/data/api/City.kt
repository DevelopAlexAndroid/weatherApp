package sib.sibintek.ru.weatherapp.data.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null
}