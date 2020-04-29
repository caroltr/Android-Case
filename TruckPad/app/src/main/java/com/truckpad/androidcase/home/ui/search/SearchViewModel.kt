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
import java.util.ArrayList

class SearchViewModel : ViewModel() {

    private lateinit var routeResult: RouteResult
    private lateinit var routePath: ArrayList<Coordinate>

    private val communicationController = CommunicationController()

    private val _result = MutableLiveData<ResultData>()
    private val _fromAddress = MutableLiveData<String>()
    private val _errorMessage = MutableLiveData<String>()
    private val _route = MutableLiveData<ArrayList<Coordinate>>()

    val result: LiveData<ResultData> = _result
    val fromAddress: LiveData<String> = _fromAddress
    val errorMessage: LiveData<String> = _errorMessage
    val route: LiveData<ArrayList<Coordinate>> = _route

    fun calcPrice(from: String, to: String, axis: String, consumption: String, fuelPrice: String) {

        if (validateInput(from, to, axis, consumption, fuelPrice)) {

            val geocodeFrom = communicationController.fetchGeocode(from)
            val geocodeTo = communicationController.fetchGeocode(to)

            lateinit var fromCity: City
            lateinit var toCity: City

            val disposable = Observable.zip(geocodeFrom, geocodeTo, geocodeBiFunction)
                .flatMap {
                    fromCity = it[0]
                    toCity = it[1]

                    communicationController.fetchRoute(
                        fromCity.coordinate,
                        toCity.coordinate,
                        consumption.toDouble(),
                        fuelPrice.toDouble()
                    )
                }
                .doOnNext { handleRouteResponse(it) }
                .flatMap { communicationController.fetchPrice(axis.toInt(), it.distance) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    val resultData = ResultData(
                        fromCity.name, toCity.name,
                        routeResult, it
                    )
                    _result.value = resultData
                    _route.value = routePath
                }, {
                    _errorMessage.value = it.message
                })
        }
    }

    fun getAddress(lat: Double, lng: Double) {
        val disposable = CommunicationController().fetchReverseGeocode(lat, lng).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe { address ->
            _fromAddress.value = address
        }
    }

    private val geocodeBiFunction =
        BiFunction<City, City, List<City>> { fromCity, toCity -> listOf(fromCity, toCity) }

    private fun handleRouteResponse(response: RouteResponse) {
        routePath = parseRoute(response.route.first())

        val distance = "${response.distance} ${response.distanceUnit}"
        val duration = "${response.duration} ${response.durationUnit}"
        val tollCost = "${response.tollCostUnit} ${response.tollCost}"
        val fuelUsage = "${response.fuelUsage} ${response.fuelUsageUnit}"
        val fuelCost = "${response.fuelCostUnit} ${response.fuelCost}"

        routeResult = RouteResult(distance, duration, tollCost, fuelUsage, fuelCost)
    }

    private fun validateInput(
        from: String,
        to: String,
        axis: String,
        consumption: String,
        fuelPrice: String
    ): Boolean {
        val emptyField =
            from.isBlank() || to.isBlank() || axis.isBlank() || consumption.isBlank() || fuelPrice.isBlank()
        return if (emptyField) {
            _errorMessage.value = "Um ou mais campos não foram preenchidos"
            false
        } else {
            val axisNum = axis.toInt()
            if (axisNum in 2..9) {
                true
            } else {
                _errorMessage.value = "A quantidade de eixos deve ser entre 2 e 9"
                false
            }
        }
    }

    private fun parseRoute(rawRoute: List<List<Double>>): ArrayList<Coordinate> {
        val route = ArrayList<Coordinate>()
        rawRoute.forEach { list ->
            route.add(Coordinate(list[0], list[1]))
        }
        return route
    }
}