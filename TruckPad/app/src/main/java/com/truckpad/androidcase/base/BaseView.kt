package com.truckpad.androidcase.base

interface BaseView<T : BasePresenter> {
    fun setPresenter(presenter: T)
}