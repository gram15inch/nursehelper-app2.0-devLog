package com.nuhlp.nursehelper.data.network.model.address


import com.nuhlp.nursehelper.data.network.model.address.Location
import com.nuhlp.nursehelper.data.network.model.address.Meta
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
    @Json(name = "documents")
    val locations: List<Location>,
    @Json(name = "meta")
    val meta: Meta
)
