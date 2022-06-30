package com.github.brkckr.park.presentation.park_detail

import com.github.brkckr.park.domain.model.ParkDetail

data class ParkDetailState(
    val parkDetail: ParkDetail? = null,
    val isLoading: Boolean = false,
    val isMapLoaded: Boolean = false
)