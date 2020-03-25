package sib.sibintek.ru.weatherapp.tools

import android.app.Activity
import android.util.Log
import java.io.IOException

class AssetsParser {

    fun loadJSONFromAssets(activity: Activity): String? {
        var json: String? = null
        try {
            val inputStream = activity.assets.open("city_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Log.d(Const.TAG_WEATHER, "UiTolls.loadJSONFromAssets - fail")
            e.printStackTrace()
        }

        return json
    }

}