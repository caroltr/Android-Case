package com.truckpad.androidcase.main

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.truckpad.androidcase.model.*
import com.truckpad.androidcase.network.ApiFactory
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.net.URLEncoder

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private lateinit var fromCoordinate: Coordinate
    private lateinit var toCoordinate: Coordinate

    fun findRoute(
        fromCoordinate: Coordinate,
        toCoordinate: Coordinate,
        fuelConsumption: Double,
        fuelPrice: Double
    ): Observable<Response<RouteResponse>> {

        val from = Place(fromCoordinate.toList())
        val to = Place(toCoordinate.toList())
        val route = RouteRequest(fuelConsumption, fuelPrice, arrayListOf(from, to))

        return ApiFactory.geoApi
            .findRoute(route)
            .toObservable()
    }

    override fun getGeocode(from: String, to: String) {

        val fromObs = fetchGeocode(from)
        val toObs = fetchGeocode(to)
        val result = Observable.zip(fromObs, toObs,
            BiFunction<Coordinate?, Coordinate?, List<Coordinate>> { fromCoord, toCoord -> listOf(fromCoord, toCoord) })
            .flatMap {
                findRoute(it[0], it[1], 5.0, 4.4)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.e("Carol", "Success")

                if (response.isSuccessful) {
                    response.body()?.let {

                    }
                }

            }, {
                Log.e("Carol", "Error")
            })
    }

    override fun getLastLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { Coordinate(it.latitude, it.longitude) }
            }
    }

    private fun fetchGeocode(address: String): Observable<Coordinate?> {
        val googleKey = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"
        val addressEncoded = URLEncoder.encode(address, "UTF-8")

        return ApiFactory.geocodeApi
            .getGeocode(addressEncoded, googleKey)
            .map { res ->
                res.body()?.let {
                    it.results.first().geometry.location
                }
            }.toObservable()
    }
}