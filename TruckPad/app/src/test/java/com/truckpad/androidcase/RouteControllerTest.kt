package com.truckpad.androidcase

import com.truckpad.androidcase.controller.RouteController
import com.truckpad.androidcase.model.Point
import com.truckpad.androidcase.model.RouteData
import com.truckpad.androidcase.model.RouteResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class RouteControllerTest {

    @Test
    fun `When get raw route response, format it to the expected way`() {
        val controller = RouteController()

        val origin = Point(listOf(-46.68664, -23.59496), "Provided")
        val destination = Point(listOf(-46.67678, -23.59867), "Provided")
        val points = listOf(origin, destination)

        val route = listOf(
            listOf(
                listOf(-46.68662, -23.59504),
                listOf(-46.68701, -23.59613),
                listOf(-46.68546, -23.59575),
                listOf(-46.67528, -23.60051),
                listOf(-46.67486, -23.59966),
                listOf(-46.6768, -23.59871)
            )
        )

        val data = RouteResponse(
            true, 1962.0, "meters", 583.0, "seconds", 1.73, "R$",
            0.39, "liters", false, points, "Maplink", route, 0.0, "R$", 0, 1.73
        )
        val result = controller.formatRouteData(data)

        val distance = "1962,00 meters"
        val duration = "583,00 seconds"
        val tollCost = "R$ 0,00"
        val fuelUsage = "0,39 liters"
        val fuelCost = "R$ 1,73"
        val total = "R$ 1,73"

        val expected = RouteData(distance, duration, tollCost, fuelUsage, fuelCost, total)
        assertThat(result, equalTo(expected))
    }

    @Test
    fun `When pass double in meters, convert it to the km`() {
        val controller = RouteController()

        val result = controller.fromMetersToKm(1962.0)
        val expected = 1.962
        assertThat(result, equalTo(expected))
    }
}