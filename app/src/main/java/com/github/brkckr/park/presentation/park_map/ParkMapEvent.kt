package com.github.brkckr.park.presentation.park_map

sealed class ParkMapEvent {
    object OnMapLoaded : ParkMapEvent()
    object OnMapSegmented : ParkMapEvent()
    object OnListSegmented : ParkMapEvent()
}