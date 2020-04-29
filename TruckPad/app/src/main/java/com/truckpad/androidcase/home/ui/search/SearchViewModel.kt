package com.truckpad.androidcase.home.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truckpad.androidcase.controller.CommunicationController
import com.truckpad.androidcase.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private lateinit var routeResult: RouteResult

    private val communicationController = CommunicationController()

    private val _result = MutableLiveData<ResultData>()
    private val _errorMessage = MutableLiveData<String>()

    val result: LiveData<ResultData> = _result
    val errorMessage: LiveData<String> = _errorMessage

    fun calcPrice(from: String, to: String, axis: String, consumption: String, fuelPrice: String) {
        val geocodeFrom = communicationController.fetchGeocode(from)
        val geocodeTo = communicationController.fetchGeocode(to)

        val disposable = Observable.zip(geocodeFrom, geocodeTo, geocodeBiFunction)
            .flatMap { communicationController.fetchRoute(it[0], it[1], consumption.toDouble(), fuelPrice.toDouble()) }
            .doOnNext { handleRouteResponse(it) }
            .flatMap { communicationController.fetchPrice(axis.toInt(), it.distance) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                val resultData = ResultData(
                    "", "",
                    routeResult, it
                )
                _result.value = resultData

            }, {
                _errorMessage.value = it.message
            })
    }

    private val geocodeBiFunction = BiFunction<Coordinate, Coordinate, List<Coordinate>> { fromCoordinate, toCoordinate -> listOf(fromCoordinate, toCoordinate) }

    private fun handleRouteResponse(response: RouteResponse) {
        val distance = "${response.distance} ${response.distanceUnit}"
        val duration = "${response.duration} ${response.durationUnit}"
        val tollCost = "${response.tollCostUnit} ${response.tollCost}"
        val fuelUsage = "${response.fuelUsage} ${response.fuelUsageUnit}"
        val fuelCost = "${response.fuelCostUnit} ${response.fuelCost}"

        routeResult = RouteResult(distance, duration, tollCost, fuelUsage, fuelCost)
    }
}