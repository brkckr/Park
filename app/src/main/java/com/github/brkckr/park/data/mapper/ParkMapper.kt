package com.github.brkckr.park.data.mapper

import com.github.brkckr.park.data.remote.dto.ParkDetailDto
import com.github.brkckr.park.data.remote.dto.ParkDto
import com.github.brkckr.park.domain.model.Park
import com.github.brkckr.park.domain.model.ParkDetail
import com.github.brkckr.park.util.parseAreaPolygon
import com.github.brkckr.park.util.parseTariff

fun ParkDto.toPark(): Park {
    return Park(
        capacity = capacity,
        district = district ?: "",
        emptyCapacity = emptyCapacity,
        freeTime = freeTime,
        isOpen = (isOpen == 1),
        lat = lat?.toDoubleOrNull() ?: 0.0,
        lng = lng?.toDoubleOrNull() ?: 0.0,
        parkId = parkId,
        parkName = parkName ?: "",
        parkType = parkType ?: "",
        workHours = workHours ?: ""
    )
}

fun ParkDetailDto.toParkDetail(): ParkDetail {
    return ParkDetail(
        address = address ?: "",
        areaPolygon = parseAreaPolygon(areaPolygon),
        capacity = capacity,
        district = district ?: "",
        emptyCapacity = emptyCapacity,
        freeTime = freeTime,
        lat = lat?.toDoubleOrNull() ?: 0.0,
        lng = lng?.toDoubleOrNull() ?: 0.0,
        locationName = locationName ?: "",
        monthlyFee = monthlyFee,
        parkId = parkId,
        parkName = parkName ?: "",
        parkType = parkType ?: "",
        tariff = parseTariff(tariff),
        updateDate = updateDate ?: "",
        workHours = workHours ?: ""
    )
}
