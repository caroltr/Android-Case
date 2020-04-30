package com.truckpad.androidcase.controller

import com.truckpad.androidcase.exception.CommunicationException
import com.truckpad.androidcase.model.*
import com.truckpad.androidcase.network.ApiFactory
import com.truckpad.androidcase.util.G_K
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
        val addressEncoded = URLEncoder.encode(address, "UTF-8")

        return ApiFactory.geocodeApi
            .getGeocode(addressEncoded, G_K)
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
            }
    }

    fun fetchReverseGeocode(lat: Double, lng: Double): Observable<String> {
        val latLng = "$lat, $lng"

        return ApiFactory.geocodeApi
            .getReverseGeocode(latLng, G_K)
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