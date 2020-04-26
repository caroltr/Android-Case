package com.truckpad.androidcase.main

import com.truckpad.androidcase.base.BasePresenter
import com.truckpad.androidcase.base.BaseView

interface MainContract {

    interface Presenter: BasePresenter {
        fun findRoute(fromLatitude: Double, fromLongitude: Double,
                      toLatitude: Double, toLongitude: Double,
                      fuelConsumption: Double, fuelPrice: Double)
    }

    interface View: BaseView<Presenter> {

    }
}