package sib.sibintek.ru.weatherapp.domain.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("id")
    @Expose
    private var id: Int = 0
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("country")
    @Expose
    private var country: String? = null
    @SerializedName("coord")
    @Expose
    private var coord: Coord? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getCoord(): Coord? {
        return coord
    }

    fun setCoord(coord: Coord?) {
        this.coord = coord
    }

}