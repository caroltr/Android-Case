package com.truckpad.androidcase.main

import com.google.android.gms.location.FusedLocationProviderClient
import com.truckpad.androidcase.base.BasePresenter
import com.truckpad.androidcase.base.BaseView

interface MainContract {

    interface Presenter: BasePresenter {
//        fun findRoute(fromLatitude: Double, fromLongitude: Double,
//                      toLatitude: Double, toLongitude: Double,
//                      fuelConsumption: Double, fuelPrice: Double)

        fun getGeocode(from: String, to: String)

        fun getLastLocation(fusedLocationClient: FusedLocationProviderClient)
    }

    interface View: BaseView<Presenter> {

    }
}