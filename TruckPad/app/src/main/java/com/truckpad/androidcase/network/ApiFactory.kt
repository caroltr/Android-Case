package com.truckpad.androidcase.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val tictacUrl = "https://tictac.api.truckpad.io/v1/"
    private const val geoUrl = "https://geo.api.truckpad.io/v1/"
    private const val googleGeocodeUrl = "https://maps.googleapis.com/maps/api/geocode/"

    private const val googleKey = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"

//    https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,
//    +Mountain+View,+CA&key=AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ

    private fun retrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    val geocodeApi: GoogleApi = retrofitBuilder()
        .baseUrl(googleGeocodeUrl).build()
        .create(GoogleApi::class.java)

    val geoApi: GeoApi = retrofitBuilder()
        .baseUrl(geoUrl).build()
        .create(GeoApi::class.java)

    val tictacApi: TictacApi = retrofitBuilder()
        .baseUrl(tictacUrl).build()
        .create(TictacApi::class.java)

}