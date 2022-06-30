package com.github.brkckr.park.domain.model

data class Park(
    val capacity: Int,
    val district: String,
    val emptyCapacity: Int,
    val freeTime: Int,
    val isOpen: Boolean,
    val lat: Double,
    val lng: Double,
    val parkId: Int,
    val parkName: String,
    val parkType: String,
    val workHours: String,
    var distance: Double = 0.0,
    var distanceDescription: String = ""
)
