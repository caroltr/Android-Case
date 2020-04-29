package com.truckpad.androidcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RouteResult (
    val distance: String,
    val duration: String,
    val tollCost: String,
    val fuelUsage: String,
    val fuelCost: String
) : Parcelable