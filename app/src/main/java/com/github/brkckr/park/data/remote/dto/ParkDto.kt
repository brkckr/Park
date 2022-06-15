package com.github.brkckr.park.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParkDto(
    @field:Json(name = "capacity") val capacity: Int,
    @field:Json(name = "district") val district: String?,
    @field:Json(name = "emptyCapacity") val emptyCapacity: Int,
    @field:Json(name = "freeTime") val freeTime: Int,
    @field:Json(name = "isOpen") val isOpen: Int,
    @field:Json(name = "lat") val lat: String?,
    @field:Json(name = "lng") val lng: String?,
    @field:Json(name = "parkID") val parkId: Int,
    @field:Json(name = "parkName") val parkName: String?,
    @field:Json(name = "parkType") val parkType: String?,
    @field:Json(name = "workHours") val workHours: String?
)
