package com.truckpad.androidcase.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double
) : Parcelable {
    fun toList(): ArrayList<Double> {
        return arrayListOf(longitude, latitude)
    }
}