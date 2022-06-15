package com.github.brkckr.park.data.remote

import com.github.brkckr.park.data.remote.dto.ParkDetailDto
import com.github.brkckr.park.data.remote.dto.ParkDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ParkApi {
    @GET("Park")
    suspend fun getParkList(): List<ParkDto>

    @GET("ParkDetay")
    suspend fun getParkDetail(
        @Query("id") id: Int,
    ): List<ParkDetailDto>

    companion object {
        const val BASE_URL = "https://api.ibb.gov.tr/ispark/"
    }
}