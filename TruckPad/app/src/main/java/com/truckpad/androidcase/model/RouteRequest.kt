package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class RouteRequest(
    @SerializedName("fuel_consumption") val fuelConsumption: Int,
    @SerializedName("fuel_price") val fuelPrice: Double,
    @SerializedName("places") val places: List<Place>
)