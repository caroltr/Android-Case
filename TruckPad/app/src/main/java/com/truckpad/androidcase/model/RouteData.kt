package com.truckpad.androidcase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RouteData (
    val distance: String,
    val duration: String,
    val tollCost: String,
    val fuelUsage: String,
    val fuelCost: String,
    val total: String
) : Parcelable