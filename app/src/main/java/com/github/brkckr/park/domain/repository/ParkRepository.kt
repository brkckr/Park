package com.github.brkckr.park.domain.repository

import com.github.brkckr.park.domain.model.Park
import com.github.brkckr.park.domain.model.ParkDetail
import com.github.brkckr.park.util.Resource

interface ParkRepository {

    suspend fun getParkList(): Resource<List<Park>>

    suspend fun getParkDetail(id: Int): Resource<ParkDetail>
}