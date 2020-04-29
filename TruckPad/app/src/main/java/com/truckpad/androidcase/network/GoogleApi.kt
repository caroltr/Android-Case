package com.truckpad.androidcase.network

import com.truckpad.androidcase.model.GeocodeResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {

    @GET("json")
    fun getGeocode(
        @Query("address") address: String,
        @Query("key") key: String
    ): Observable<Response<GeocodeResponse>>

    @GET("json")
    fun getReverseGeocode(
        @Query("latlng") latlng: String,
        @Query("key") key: String
    ): Observable<Response<GeocodeResponse>>


    // https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=YOUR_API_KEY

}