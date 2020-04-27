package com.truckpad.androidcase.main

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.truckpad.androidcase.network.ApiFactory
import com.truckpad.androidcase.model.Coordinate
import com.truckpad.androidcase.model.Place
import com.truckpad.androidcase.model.RouteRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun findRoute(
        fromLatitude: Double,
        fromLongitude: Double,
        toLatitude: Double,
        toLongitude: Double,
        fuelConsumption: Double,
        fuelPrice: Double
    ) {
        val fromLocation = Coordinate(fromLatitude, fromLongitude)
        val toLocation = Coordinate(toLatitude, toLongitude)

        val from = Place(fromLocation.toList())
        val to = Place(toLocation.toList())
        val route = RouteRequest(fuelConsumption, fuelPrice, arrayListOf(from, to))

        val r = ApiFactory.geoApi
            .findRoute(route)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.e("Carol", "Success")
            }, {
                Log.e("Carol", "Error")
            })
    }

    override fun getGeocode(from: String, to: String) {
        fetchGeocode(from)
        fetchGeocode(to)
    }

    override fun getLastLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { Coordinate(it.latitude, it.longitude) }
            }
    }

    private fun fetchGeocode(address: String) {
        val googleKey = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"
        val addressEncoded = URLEncoder. encode(address,"UTF-8");

        val result1 = ApiFactory.geocodeApi
            .getGeocode(addressEncoded, googleKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.body()?.let {
                    it.results.first().geometry.location
                }

                Log.e("Carol", "Success")
            }, {
                Log.e("Carol", "Error")
            })
    }
}