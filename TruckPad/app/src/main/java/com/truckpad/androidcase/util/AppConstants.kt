package com.truckpad.androidcase.util

enum class RequestCode(val code: Int) {
    PERMISSION_LOCATION(100);
}

enum class Extra(val value: String) {
    PRICE("extra_price"),
    ROUTE("route_price");
}