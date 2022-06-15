package com.github.brkckr.park.domain.model

import com.google.android.gms.maps.model.LatLng

data class ParkDetail(
    val address: String,
    val areaPolygon: List<LatLng>,
    val capacity: Int,
    val district: String,
    val emptyCapacity: Int,
    val freeTime: Int,
    val lat: Double,
    val lng: Double,
    val locationName: String,
    val monthlyFee: Double,
    val parkId: Int,
    val parkName: String,
    val parkType: String,
    val tariff: List<String>,
    val updateDate: String,
    val workHours: String
)
