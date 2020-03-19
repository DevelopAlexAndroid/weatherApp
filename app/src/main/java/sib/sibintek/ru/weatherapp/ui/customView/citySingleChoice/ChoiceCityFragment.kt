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
import sib.sibintek.ru.weatherapp.data.data.api.ListCity
import sib.sibintek.ru.weatherapp.tools.Const
import java.io.IOException
import kotlin.concurrent.thread

class ChoiceCityFragment(private val callbackListener: CityHolder.CallbackItemClick) :
    DialogFragment() {

    companion object {
        const val TAG_CHOICE_CITY = "ChoiceCityFragment_TAG"
    }

    private var cityList = ListCity()
    //Флаг говорящий статус обработки json из файла
    var statusJsonParsing = false

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

    fun parseCityJson(activity: Activity) {
        Log.d(Const.TAG_WEATHER, "ChoiceCityFragment.parseCityJson - start")
        thread {
            val listModels = ListCity()
            val model = Gson().fromJson(loadJSONFromAssets(activity), ListCity::class.java)

            model.items.forEach {
                if (it.country == "RU")
                    listModels.items.add(it)
            }
            activity.runOnUiThread {
                this.cityList = listModels
                statusJsonParsing = true
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
            Log.d(Const.TAG_WEATHER, "ChoiceCityFragment.loadJSONFromAssets - fail")
            e.printStackTrace()
        }

        return json
    }

}
