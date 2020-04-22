package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("point") val point: List<Double>
)