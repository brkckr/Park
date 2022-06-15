package com.github.brkckr.park.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParkDetailDto(
    @field:Json(name = "address") val address: String?,
    @field:Json(name = "areaPolygon") val areaPolygon: String?,
    @field:Json(name = "capacity") val capacity: Int,
    @field:Json(name = "district") val district: String?,
    @field:Json(name = "emptyCapacity") val emptyCapacity: Int,
    @field:Json(name = "freeTime") val freeTime: Int,
    @field:Json(name = "lat") val lat: String?,
    @field:Json(name = "lng") val lng: String?,
    @field:Json(name = "locationName") val locationName: String?,
    @field:Json(name = "monthlyFee") val monthlyFee: Double,
    @field:Json(name = "parkID") val parkId: Int,
    @field:Json(name = "parkName") val parkName: String?,
    @field:Json(name = "parkType") val parkType: String?,
    @field:Json(name = "tariff") val tariff: String?,
    @field:Json(name = "updateDate") val updateDate: String?,
    @field:Json(name = "workHours") val workHours: String?
)