package com.truckpad.androidcase.main

import com.google.android.gms.location.FusedLocationProviderClient
import com.truckpad.androidcase.base.BasePresenter
import com.truckpad.androidcase.base.BaseView

interface MainContract {

    interface Presenter: BasePresenter {

        fun calcPrice(from: String, to: String, axes: String, consumption: String)

        fun getLastLocation(fusedLocationClient: FusedLocationProviderClient)
    }

    interface View: BaseView<Presenter> {

    }
}