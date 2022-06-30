package com.github.brkckr.park.presentation.park_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.brkckr.park.domain.repository.ParkRepository
import com.github.brkckr.park.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ParkRepository
) : ViewModel() {

    var state by mutableStateOf(ParkDetailState())

    init {
        viewModelScope.launch {
            val parkId = savedStateHandle.get<Int>("id") ?: return@launch

            state = state.copy(isLoading = true)

            val parkDetailResult = repository.getParkDetail(parkId)

            when (parkDetailResult) {
                is Resource.Success -> {
                    state = state.copy(
                        parkDetail = parkDetailResult.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        parkDetail = null,
                        isLoading = false
                    )
                }
                else -> Unit
            }
        }
    }

    fun onEvent(event: ParkDetailEvent) {
        when (event) {
            is ParkDetailEvent.OnMapLoaded -> {
                state = state.copy(isMapLoaded = true)
            }
        }
    }
}