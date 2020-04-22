package com.truckpad.androidcase.api

import com.truckpad.androidcase.model.RouteRequest
import com.truckpad.androidcase.model.RouteResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("route")
    fun findRoute(
        @Body body: RouteRequest): Single<Response<RouteResponse>>
}