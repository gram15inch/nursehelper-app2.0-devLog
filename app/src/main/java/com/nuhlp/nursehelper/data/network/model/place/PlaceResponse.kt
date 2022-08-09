package com.nuhlp.nursehelper.data.network.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceResponse(
    @Json(name = "documents")
    val places: List<Place>,
    @Json(name = "meta")
    val meta: Meta
)