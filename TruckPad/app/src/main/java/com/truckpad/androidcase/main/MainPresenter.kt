package com.truckpad.androidcase.main

import android.util.Log
import com.truckpad.androidcase.network.ApiFactory
import com.truckpad.androidcase.model.Location
import com.truckpad.androidcase.model.Place
import com.truckpad.androidcase.model.RouteRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun findRoute(
        fromLatitude: Double,
        fromLongitude: Double,
        toLatitude: Double,
        toLongitude: Double,
        fuelConsumption: Double,
        fuelPrice: Double
    ) {
        val fromLocation = Location(fromLatitude, fromLongitude)
        val toLocation = Location(toLatitude, toLongitude)

        val from = Place(fromLocation.toList())
        val to = Place(toLocation.toList())
        val route = RouteRequest(fuelConsumption, fuelPrice, arrayListOf(from, to))

        val r = ApiFactory.geoApi
            .findRoute(route)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.e("Carol", "Success")
            }, {
                Log.e("Carol", "Error")
            })
    }
}