package com.truckpad.androidcase.model

import com.google.gson.annotations.SerializedName

data class GeocodeResponse(
    @SerializedName("results") val results: List<GeocodeResult>,
    @SerializedName("status")  val status: String
)

data class GeocodeResult(
    @SerializedName("address_components") val addressComponents: List<AddressComponent>,
    @SerializedName("formatted_address") val formattedAddress: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("place_id") val placeId: String,
    @SerializedName("types") val types: List<String>
)

data class AddressComponent(
    @SerializedName("long_name") val longName: String,
    @SerializedName("short_name") val shortName: String,
    @SerializedName("types") val types: List<String>
)

data class Geometry(
    @SerializedName("location") val location: Coordinate,
    @SerializedName("location_type") val locationType: String,
    @SerializedName("viewport") val viewport: Viewport
)

data class Viewport(
    @SerializedName("northeast") val northeast: Coordinate,
    @SerializedName("southwest") val southwest: Coordinate
)