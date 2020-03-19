package sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_item.view.*
import sib.sibintek.ru.weatherapp.data.data.api.City

class CityHolder(itemView: View, private val callback: CallbackItemClick) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: City){
        itemView.city_name.text = item.name
        itemView.setOnClickListener {
            callback.onItemClicked(item.id)
        }
    }

    interface CallbackItemClick {
        fun onItemClicked(idCity: Int)
    }

}