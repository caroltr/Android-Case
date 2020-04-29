package com.truckpad.androidcase.home.result

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truckpad.androidcase.controller.RouteController
import com.truckpad.androidcase.model.PriceResponse
import com.truckpad.androidcase.model.ResultData
import com.truckpad.androidcase.model.RouteData
import com.truckpad.androidcase.util.Extra

class ResultViewModel : ViewModel() {

    private val routeController = RouteController()

    private val _from = MutableLiveData<String>()
    private val _to = MutableLiveData<String>()
    private val _prices = MutableLiveData<PriceResponse>()
    private val _routeData = MutableLiveData<RouteData>()

    val from: LiveData<String> = _from
    val to: LiveData<String> = _to
    val prices: LiveData<PriceResponse> = _prices
    val routeData: LiveData<RouteData> = _routeData

    fun handleData(arguments: Bundle?) {
        arguments?.get(Extra.PRICE.value)?.let {
            (it as? ResultData)?.let { result -> handleResult(result) }
        }
    }

    private fun handleResult(result: ResultData) {
        _from.value = result.from
        _to.value = result.to
        _prices.value = result.priceResponse
        _routeData.value = routeController.formatRouteData(result.routeResult)
    }
}