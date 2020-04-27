package com.truckpad.androidcase.main

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.truckpad.androidcase.model.*
import com.truckpad.androidcase.network.ApiFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.net.URLEncoder

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun calcPrice(from: String, to: String, axes: String, consumption: String) {

        val fromObs = fetchGeocode(from)
        val toObs = fetchGeocode(to)

        val result = Observable.zip(fromObs, toObs,
            BiFunction<Coordinate?, Coordinate?, List<Coordinate>> {fromCoord, toCoord ->
                listOf(fromCoord, toCoord) })
            .flatMap {
                fetchRoute(it[0], it[1], consumption.toDouble(), 4.4)
            }
            .flatMap {
                it.body()?.let {routeResponse ->
                    fetchPrice(axes.toInt(), routeResponse.distance)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.e("Carol", "Success")
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

    private fun fetchPrice(axis: Int, distance: Double): Observable<PriceResponse> {
        val body = PriceRequest(axis, distance, true)
        return ApiFactory.tictacApi.findPrice(body).map {
            it.body()
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

//                    val latitude = "%.5f".format(coordinate.latitude).toDouble()
//                    val longitude = "%.5f".format(coordinate.longitude).toDouble()

//                    Coordinate(latitude, longitude)
                }
            }
    }

    private fun fetchRoute(
        fromCoordinate: Coordinate,
        toCoordinate: Coordinate,
        fuelConsumption: Double,
        fuelPrice: Double
    ): Observable<Response<RouteResponse>> {

        val from = Place(fromCoordinate.toList())
        val to = Place(toCoordinate.toList())
        val places = arrayListOf(from, to)

        val bodyRequest = RouteRequest(fuelConsumption, fuelPrice, places)

        return ApiFactory.geoApi.findRoute(bodyRequest)
    }
}