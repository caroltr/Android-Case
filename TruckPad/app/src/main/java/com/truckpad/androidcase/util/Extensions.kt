package com.truckpad.androidcase.util

fun Double.asReais(): String {
    val formatted = "%.2f".format(this)
    return "R$ $formatted"
}

fun Double.format2Dec() : String{
    return "%.2f".format(this)
}