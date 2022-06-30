package com.github.brkckr.park.presentation.park_detail

sealed class ParkDetailEvent {
    object OnMapLoaded : ParkDetailEvent()
}