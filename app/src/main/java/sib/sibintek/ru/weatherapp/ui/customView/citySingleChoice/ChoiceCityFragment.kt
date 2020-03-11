package sib.sibintek.ru.weatherapp.ui.customView.citySingleChoice

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_choice_city.*
import sib.sibintek.ru.weatherapp.R
import sib.sibintek.ru.weatherapp.domain.data.api.ListCity
import sib.sibintek.ru.weatherapp.tools.Const
import sib.sibintek.ru.weatherapp.tools.Const.FLAG_JSON_PARSING
import java.io.IOException
import kotlin.concurrent.thread

class ChoiceCityFragment : DialogFragment() {

    companion object {
        const val TAG_CHOICE_CITY = "ChoiceCityFragment_TAG"
        //test
        //test2
        //test3
    }

    private var cityList = ListCity()
    private lateinit var callbackListener: CityHolder.CallbackItemClick

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_choice_city, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CityRecycler(callbackListener, cityList.items)
        val cityRecycler = view.findViewById(R.id.city_recycler) as RecyclerView
        cityRecycler.layoutManager = LinearLayoutManager(this.activity)
        cityRecycler.adapter = adapter
        cancel.setOnClickListener { dismiss() }
    }

    fun addListener(callbackListener: CityHolder.CallbackItemClick) {
        this.callbackListener = callbackListener
    }

    fun parseCityJson(activity: Activity) {
        Log.d(Const.TAG_WEATHER, "ChoiceCityFragment.parseCityJson - start")
        thread {
            val listModels = ListCity()
            val model = Gson().fromJson(loadJSONFromAssets(activity), ListCity::class.java)

            model.items.forEach {
                if (it.getCountry().equals("RU"))
                    listModels.items.add(it)
            }
            activity.runOnUiThread {
                this.cityList = model
                FLAG_JSON_PARSING = true
                Log.d(Const.TAG_WEATHER, "ChoiceCityFragment.parseCityJson - finish")
            }
        }
    }

    private fun loadJSONFromAssets(activity: Activity): String? {
        var json: String? = null
        try {
            val inputStream = activity.assets.open("city_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }

}
