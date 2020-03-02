package sib.sibintek.ru.weatherapp.domain.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Wind {
    @SerializedName("speed")
    @Expose
    val speed: Double? = null
    @SerializedName("deg")
    @Expose
    val deg: Int? = null
}