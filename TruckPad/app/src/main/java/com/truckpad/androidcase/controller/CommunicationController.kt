package com.truckpad.androidcase.controller

import com.truckpad.androidcase.exception.CommunicationException
import com.truckpad.androidcase.model.*
import com.truckpad.androidcase.network.ApiFactory
import io.reactivex.Observable
import java.net.URLEncoder

class CommunicationController {

    fun fetchRoute(
        fromCoordinate: Coordinate,
        toCoordinate: Coordinate,
        fuelConsumption: Double,
        fuelPrice: Double
    ): Observable<RouteResponse> {
        val from = Place(fromCoordinate.toList())
        val to = Place(toCoordinate.toList())
        val places = arrayListOf(from, to)

        val bodyRequest = RouteRequest(fuelConsumption, fuelPrice, places)

        return ApiFactory.geoApi
            .findRoute(bodyRequest)
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        it
                    } ?: run {
                        error(CommunicationException())
                    }
                } else {
                    error(CommunicationException())
                }
            }
    }

    fun fetchPrice(axis: Int, distance: Double): Observable<PriceResponse> {
        val body = PriceRequest(axis, distance, true)
        return ApiFactory.tictacApi
            .findPrice(body)
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        it
                    } ?: run {
                        error(CommunicationException())
                    }
                } else {
                    error(CommunicationException())
                }
            }
    }

    fun fetchGeocode(address: String): Observable<City> {
        val googleKey = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"
        val addressEncoded = URLEncoder.encode(address, "UTF-8")

        return ApiFactory.geocodeApi
            .getGeocode(addressEncoded, googleKey)
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        val name = it.results.first().formattedAddress
                        val coordinate = it.results.first().geometry.location

                        City(name, coordinate)
                    } ?: run {
                        error(CommunicationException())
                    }
                } else {
                    error(CommunicationException())
                }
//                    val latitude = "%.5f".format(coordinate.latitude).toDouble()
//                    val longitude = "%.5f".format(coordinate.longitude).toDouble()
//                    Coordinate(latitude, longitude)\
            }
    }

    fun fetchReverseGeocode(lat: Double, lng: Double): Observable<String> {
        val googleKey = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"
        val latLng = "$lat, $lng"

        return ApiFactory.geocodeApi
            .getReverseGeocode(latLng, googleKey)
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.results.first().formattedAddress
                    } ?: run {
                        error(CommunicationException())
                    }
                } else {
                    error(CommunicationException())
                }
            }
    }
}