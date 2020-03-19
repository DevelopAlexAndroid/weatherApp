package sib.sibintek.ru.weatherapp.data.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiWeatherModel {

    @SerializedName("id")
    @Expose
    val id: Int? = null
    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("cod")
    @Expose
    val cod: Int? = null
    @SerializedName("base")
    @Expose
    val base: String? = null
    @SerializedName("dt")
    @Expose
    val dt: Int? = null

    @SerializedName("coord")
    @Expose
    val coord: Coord? = null
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>? = null
    @SerializedName("main")
    @Expose
    val main: Main? = null
    @SerializedName("visibility")
    @Expose
    val visibility: Int? = null
    @SerializedName("wind")
    @Expose
    val wind: Wind? = null
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds? = null
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null
}
