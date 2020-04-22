package com.truckpad.androidcase.main

import com.truckpad.androidcase.base.BasePresenter
import com.truckpad.androidcase.base.BaseView

interface MainContract {

    interface Presenter: BasePresenter {

    }

    interface View: BaseView<Presenter> {

    }
}