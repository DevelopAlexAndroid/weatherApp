package sib.sibintek.ru.weatherapp.tools

object Const {

    /**Ключ пользователя для запросов API*/
    const val KEY_API = "b6907d289e10d714a6e88b30761fae22"

    /**Ключ для сохранения/загрузки правильного обновления данных при старте приложения*/
    const val KEY_LOCATION_OR_CITY = "KEY_UPDATE_DATA"

    /**Ключ для сохранения/загрузки выбранного города пользователем*/
    const val KEY_USER_CHOICE_CITY = "KEY_USER_CHOICE_CITY"

    /**Ключ для хранения/загрузки последнего времени запроса*/
    const val KEY_TIME_LAST_CAll = "KEY_TIME_LAST_CAll"

    /**TAG для логирования*/
    const val TAG_WEATHER = "===== TAG_WEATHER ====="

    const val FIRST_START = 0
    const val LOAD_GEOLOCATION = 111
    const val LOAD_CITY = 222
    const val LOAD_DATA_FROM_DATABASE = 666
    const val LOAD_CITY_DEFAULT_KRASNODAR = 542420
    //Код удачного ответа сервера
    const val CALL_SUCCESS = 200

    /**Время по истечению которого, данные в базе будут считаться устаревшими*/
    const val TIME_CORRECT_DATA = 10 * 60 * 1000

}