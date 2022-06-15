package com.github.brkckr.park.util

import com.google.android.gms.maps.model.LatLng

fun parseTariff(tariff: String?): List<String> {
    return tariff?.split(";")?.map { it.trim() } ?: emptyList()
}

fun parseAreaPolygon(areaPolygon: String?): List<LatLng> {
    val polygonList = mutableSetOf<LatLng>()

    try {
        areaPolygon?.replace(("[^\\d., ]").toRegex(), "")?.trim()?.split(",")
            ?.map { polygonString ->
                val latlng = polygonString.split(" ")
                polygonList.add(LatLng(latlng[0].toDouble(), latlng[1].toDouble()))
            }
    } catch (e: Exception) {

    }

    return polygonList.toList()
}
