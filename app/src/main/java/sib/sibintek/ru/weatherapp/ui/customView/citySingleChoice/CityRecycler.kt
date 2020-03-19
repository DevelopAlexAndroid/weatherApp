package sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.data.data.api.City


class CityRecycler(
    private var callbackListener: CityHolder.CallbackItemClick,
    private var cityList: List<City>
) : RecyclerView.Adapter<CityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityHolder(v, callbackListener)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}