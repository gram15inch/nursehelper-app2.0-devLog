package com.nuhlp.nursehelper.data.network.model.place


import com.nuhlp.nursehelper.data.network.model.NullToEmptyString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "is_end")
    val isEnd: Boolean,
    @Json(name = "pageable_count")
    val pageableCount: Int,
    @Json(name = "same_name")
    @NullToEmptyString
    val sameName: String,
    @Json(name = "total_count")
    val totalCount: Int
)