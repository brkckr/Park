package com.github.brkckr.park.presentation.park_map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.brkckr.park.domain.model.Park
import com.github.brkckr.park.domain.repository.ParkRepository
import com.github.brkckr.park.util.Resource
import com.github.brkckr.park.util.parseDistance
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkMapViewModel @Inject constructor(
    private val repository: ParkRepository
) : ViewModel() {

    var state by mutableStateOf(ParkMapState())

    init {
        viewModelScope.launch {

            state = state.copy(isLoading = true)

            val result = repository.getParkList()

            when (result) {
                is Resource.Success -> {
                    result.data?.let { parks ->

                        val parkList = computeDistanceBetween(parks, state.currentLocation)
                        val clusterList = generateParkClusterList(parkList)

                        state = state.copy(
                            parkList = parkList,
                            parkClusterList = clusterList
                        )
                    }
                    state = state.copy(isLoading = false)
                }
                is Resource.Error -> {
                    state = state.copy(
                        parkList = emptyList(),
                        parkClusterList = emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Loading -> Unit
            }
        }
    }

    private fun generateParkClusterList(parkList: List<Park>): List<ParkClusterItem> {
        val parkClusterList: MutableList<ParkClusterItem> = mutableListOf()

        for (park in parkList) {
            parkClusterList.add(
                ParkClusterItem(
                    LatLng(park.lat, park.lng),
                    park.parkName,
                    park.distanceDescription,
                    park.parkId
                )
            )
        }

        return parkClusterList.toList()
    }

    private fun computeDistanceBetween(parkList: List<Park>, currentLocation: LatLng): List<Park> {
        for (park in parkList) {
            val distance = SphericalUtil.computeDistanceBetween(
                currentLocation,
                LatLng(park.lat, park.lng)
            )
            park.distance = distance
            park.distanceDescription = parseDistance(distance)
        }

        return parkList.sortedBy { it.distance }
    }

    fun onEvent(event: ParkMapEvent) {
        when (event) {
            is ParkMapEvent.OnMapSegmented -> {
                state = state.copy(isListVisible = false)
            }

            is ParkMapEvent.OnListSegmented -> {
                state = state.copy(isListVisible = true)
            }

            is ParkMapEvent.OnMapLoaded -> {
                state = state.copy(isMapLoaded = true)
            }
        }
    }
}