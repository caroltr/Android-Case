package com.truckpad.androidcase.model

data class Location(
    val latitude: Double,
    val longitude: Double
) {
    fun toList(): ArrayList<Double> {
        return arrayListOf(latitude, longitude)
    }
}