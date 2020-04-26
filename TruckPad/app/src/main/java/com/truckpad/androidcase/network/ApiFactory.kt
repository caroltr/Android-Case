package com.truckpad.androidcase.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val tictacUrl = "https://tictac.api.truckpad.io/v1/"
    private const val geoUrl = "https://geo.api.truckpad.io/v1/"

    private fun retrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    val geoApi: GeoApi = retrofitBuilder()
        .baseUrl(geoUrl).build()
        .create(GeoApi::class.java)

    val tictacApi: TictacApi = retrofitBuilder()
        .baseUrl(tictacUrl).build()
        .create(TictacApi::class.java)

}