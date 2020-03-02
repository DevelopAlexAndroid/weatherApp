package sib.sibintek.ru.weatherapp.provides

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import dagger.android.support.DaggerAppCompatActivity
import sib.sibintek.ru.weatherapp.tools.Const.TAG_WEATHER

class LocationProviders {

    companion object {
        const val PERMISSION_ID = 111
    }

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var geolocationStatus: GeolocationStatus? = null

    fun constructor(
        geolocationStatus: GeolocationStatus,
        mFusedLocationClient: FusedLocationProviderClient
    ) {

        this.mFusedLocationClient = mFusedLocationClient
        this.geolocationStatus = geolocationStatus
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(activity: Activity) {
        if (checkPermissions(activity)) {
            if (isLocationEnabled(activity)) {
                mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData(activity)
                    } else {
                        //Получение данных
                        geolocationStatus?.completeRequest(location.latitude, location.longitude)
                        Log.d(TAG_WEATHER, "LocationProviders : lat = $location.latitude.toString() : lon = $location.longitude.toString() ")
                    }
                }
            } else {
                Toast.makeText(activity, "Пожулайста, включите GPS-датчик", Toast.LENGTH_LONG).show()
                val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        } else {
            requestPermissions(activity)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(activity: Activity) {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            //Получение данных если при выключение датчика последние данные затёрлись
            geolocationStatus?.completeRequest(mLastLocation.latitude, mLastLocation.longitude)
            Log.d(TAG_WEATHER, "LocationProviders : lat = $mLastLocation.latitude.toString() : lon = $mLastLocation.longitude.toString() ")
        }
    }

    private fun isLocationEnabled(activity: Activity): Boolean {
        val locationManager: LocationManager =
            activity.getSystemService(DaggerAppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

}

interface GeolocationStatus {
    fun completeRequest(lat: Double, lon: Double)
}