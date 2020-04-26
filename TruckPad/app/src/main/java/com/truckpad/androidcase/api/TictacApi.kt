package com.truckpad.androidcase.api

import com.truckpad.androidcase.model.PriceRequest
import com.truckpad.androidcase.model.PriceResponse
import com.truckpad.androidcase.model.RouteRequest
import com.truckpad.androidcase.model.RouteResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TictacApi {

    @POST("antt_price/all")
    fun findRoute(
        @Body body: PriceRequest): Single<Response<PriceResponse>>

}