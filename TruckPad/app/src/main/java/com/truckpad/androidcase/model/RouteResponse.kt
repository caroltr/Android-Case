package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("cached") val cached: Boolean,
    @SerializedName("distance") val distance: Double,
    @SerializedName("distance_unit") val distanceUnit: String,
    @SerializedName("duration") val duration: Double,
    @SerializedName("duration_unit") val durationUnit: String,
    @SerializedName("fuel_cost") val fuelCost: Double,
    @SerializedName("fuel_cost_unit") val fuelCostUnit: String,
    @SerializedName("fuel_usage") val fuelUsage: Double,
    @SerializedName("fuel_usage_unit") val fuelUsageUnit: String,
    @SerializedName("has_tolls") val hasTolls: Boolean,
    @SerializedName("points") val points: List<Point>,
    @SerializedName("provider") val provider: String,
    @SerializedName("route") val route: List<List<List<Double>>>,
    @SerializedName("toll_cost") val tollCost: Double,
    @SerializedName("toll_cost_unit") val tollCostUnit: String,
    @SerializedName("toll_count") val tollCount: Int,
    @SerializedName("total_cost") val totalCost: Double
)

data class Point(
    @SerializedName("point") val point: List<Double>,
    @SerializedName("provider") val provider: String
)