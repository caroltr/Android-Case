package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class RouteRequest(
    @SerializedName("fuel_consumption") val fuelConsumption: Double,
    @SerializedName("fuel_price") val fuelPrice: Double,
    @SerializedName("places") val places: List<Place>
)

data class Place(
    @SerializedName("point") val point: List<Double>
)