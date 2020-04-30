package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class PriceRequest(
    @SerializedName("axis") val axis: Int,
    @SerializedName("distance") val distance: Double,
    @SerializedName("has_return_shipment") val hasReturnShipment: Boolean
)