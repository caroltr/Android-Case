package com.truckpad.androidcase.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceResponse(
    @SerializedName("frigorificada") val frigorificada: Double,
    @SerializedName("geral") val geral: Double,
    @SerializedName("granel") val granel: Double,
    @SerializedName("neogranel") val neogranel: Double,
    @SerializedName("perigosa") val perigosa: Double
) : Parcelable