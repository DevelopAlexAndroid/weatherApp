package sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_item.view.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.domain.data.api.City
import javax.security.auth.callback.Callback

class CityHolder(itemView: View, private val callback: CallbackItemClick) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: City){
        itemView.city_name.text = item.getName()
        itemView.setOnClickListener {
            callback.onItemClicked(item.getId())
        }
    }

    interface CallbackItemClick {
        fun onItemClicked(idCity: Int)
    }

}