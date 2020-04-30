package com.truckpad.androidcase.util

enum class RequestCode(val code: Int) {
    PERMISSION_LOCATION(100);
}

enum class Extra(val value: String) {
    PRICE("extra_price");
}

// Temp key - need to be removed from public repo and shouldn't be stored here
const val G_K = "AIzaSyBW2GhHdV3obS6jEkTGHjod5REtuSSKFcQ"