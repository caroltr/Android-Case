package com.truckpad.androidcase.network

import com.truckpad.androidcase.model.RouteRequest
import com.truckpad.androidcase.model.RouteResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GeoApi {

    @POST("route")
    fun findRoute(
        @Body body: RouteRequest
    ): Observable<Response<RouteResponse>>

}