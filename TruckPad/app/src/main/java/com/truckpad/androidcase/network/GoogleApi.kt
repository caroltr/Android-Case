package com.truckpad.androidcase.network

import com.truckpad.androidcase.model.GeocodeResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("json")
    fun getGeocode(
        @Query("address") address: String,
        @Query("key") key: String
    ): Single<Response<GeocodeResponse>>

}