package com.truckpad.androidcase.home.ui.search

import android.util.Log
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

    private val communicationController = CommunicationController()

    private val _result = MutableLiveData<PriceResponse>()

    val result: LiveData<PriceResponse> = _result

    fun calcPrice(from: String, to: String, axis: String, consumption: String, fuelPrice: String) {
        val geocodeFrom = communicationController.fetchGeocode(from)
        val geocodeTo = communicationController.fetchGeocode(to)

        val disposable = Observable.zip(geocodeFrom, geocodeTo, geocodeBiFunction)
            .flatMap { communicationController.fetchRoute(it[0], it[1], consumption.toDouble(), fuelPrice.toDouble()) }
            .flatMap { communicationController.fetchPrice(axis.toInt(), it.distance)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _result.value = response
                Log.e("Carol", "Success")
            }, {
                Log.e("Carol", "Error")
            })
    }

    private val geocodeBiFunction = BiFunction<Coordinate, Coordinate, List<Coordinate>> { fromCoordinate, toCoordinate -> listOf(fromCoordinate, toCoordinate) }

}