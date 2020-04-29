package com.truckpad.androidcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultData(
    val from: String,
    val to: String,
    val routeResult: RouteResult,
    val priceResponse: PriceResponse
) : Parcelable