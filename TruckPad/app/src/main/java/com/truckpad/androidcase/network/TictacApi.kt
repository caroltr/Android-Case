package com.truckpad.androidcase.network

import com.truckpad.androidcase.model.PriceRequest
import com.truckpad.androidcase.model.PriceResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TictacApi {

    @POST("antt_price/all")
    fun findPrice(
        @Body body: PriceRequest): Observable<Response<PriceResponse>>

}