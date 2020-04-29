package com.truckpad.androidcase.controller

import com.truckpad.androidcase.model.RouteData
import com.truckpad.androidcase.model.RouteResponse
import com.truckpad.androidcase.util.asReais
import com.truckpad.androidcase.util.format2Dec

class RouteController {

    fun formatRouteData(response: RouteResponse): RouteData {
        val distance = "${response.distance.format2Dec()} ${response.distanceUnit}"
        val duration = "${response.duration.format2Dec()} ${response.durationUnit}"
        val tollCost = "${response.tollCostUnit} ${response.tollCost.format2Dec()}"
        val fuelUsage = "${response.fuelUsage.format2Dec()} ${response.fuelUsageUnit}"
        val fuelCost = "${response.fuelCostUnit} ${response.fuelCost.format2Dec()}"
        val total = response.totalCost.asReais()

        return RouteData(distance, duration, tollCost, fuelUsage, fuelCost, total)
    }

    fun fromMetersToKm(value: Double): Double = (value / 1000)
}