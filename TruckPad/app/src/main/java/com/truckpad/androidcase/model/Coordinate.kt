package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double
) {
    fun toList(): ArrayList<Double> {
        return arrayListOf(longitude, latitude)
    }
}