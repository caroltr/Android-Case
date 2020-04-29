package com.truckpad.androidcase.home.ui.result

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truckpad.androidcase.model.Coordinate
import com.truckpad.androidcase.model.ResultData
import com.truckpad.androidcase.util.Extra
import java.util.ArrayList

class ResultViewModel : ViewModel() {

    private val _result = MutableLiveData<ResultData>()
    private val _route = MutableLiveData<ArrayList<Coordinate>>()

    val result: LiveData<ResultData> = _result
    val route: LiveData<ArrayList<Coordinate>> = _route

    fun handleData(arguments: Bundle?) {
        arguments?.get(Extra.PRICE.value)?.let {
            (it as? ResultData)?.let { result ->
                val route = parseRoute(result.routeResult.route)

                _result.value = result
                _route.value = route
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