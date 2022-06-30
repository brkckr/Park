package com.github.brkckr.park.presentation.park_map

import com.github.brkckr.park.domain.model.Park
import com.google.android.gms.maps.model.LatLng

data class ParkMapState(
    val parkList: List<Park> = emptyList(),
    val parkClusterList: List<ParkClusterItem> = emptyList(),
    val currentLocation: LatLng = LatLng(41.0255648, 28.9720744),
    val isLoading: Boolean = false,
    val isMapLoaded: Boolean = false,
    val isListVisible: Boolean = false
)