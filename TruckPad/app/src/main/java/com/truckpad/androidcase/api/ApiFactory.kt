package com.truckpad.androidcase.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    //    private const val url = "https://tictac.api.truckpad.io/v1/antt_price/all"
    private const val url = "https://geo.api.truckpad.io/v1/"

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val api: Api = retrofit()
        .create(Api::class.java)
}