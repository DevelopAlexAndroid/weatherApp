package sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_choice_city.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.data.data.api.City

class ChoiceCityFragment(private val callbackListener: CityHolder.CallbackItemClick) :
    DialogFragment() {

    companion object {
        const val TAG_CHOICE_CITY = "ChoiceCityFragment_TAG"
    }

    private lateinit var cityList: ArrayList<City>
    private lateinit var adapter: CityRecycler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_choice_city, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        city_recycler.layoutManager = LinearLayoutManager(this.activity)
        adapter = CityRecycler(callbackListener, cityList)
        city_recycler.adapter = adapter
        cancel.setOnClickListener { dismiss() }
    }

    fun addedListCities(cities: ArrayList<City>) {
        this.cityList = cities
    }

}
