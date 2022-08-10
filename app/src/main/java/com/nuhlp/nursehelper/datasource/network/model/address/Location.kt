package com.nuhlp.nursehelper.datasource.network.model.address


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address")
    val address: Address,
    @Json(name = "address_name")
    val addressName: String,
    @Json(name = "address_type")
    val addressType: String,
    @Json(name = "road_address")
    val roadAddress: RoadAddress,
    @Json(name = "x")
    val x: String,
    @Json(name = "y")
    val y: String
)