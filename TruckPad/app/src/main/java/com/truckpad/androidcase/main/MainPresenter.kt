package com.truckpad.androidcase.main

import android.util.Log
import com.truckpad.androidcase.api.ApiFactory
import com.truckpad.androidcase.model.Place
import com.truckpad.androidcase.model.RouteRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun findRoute() {
        val from = Place(listOf(-46.68664, -23.59496))
        val to = Place(listOf(-46.67678, -23.59867))
        val route = RouteRequest(5, 4.4, listOf(from, to))

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