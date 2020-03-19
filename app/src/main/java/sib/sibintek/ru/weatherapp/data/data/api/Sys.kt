package sib.sibintek.ru.weatherapp.data.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys {
    @SerializedName("type")
    @Expose
    val type: Int? = null
    @SerializedName("id")
    @Expose
    val id: Int? = null
    @SerializedName("message")
    @Expose
    val message: Double? = null
    @SerializedName("country")
    @Expose
    val country: String? = null
    @SerializedName("sunrise")
    @Expose
    val sunrise: Int? = null
    @SerializedName("sunset")
    @Expose
    val sunset: Int? = null
}