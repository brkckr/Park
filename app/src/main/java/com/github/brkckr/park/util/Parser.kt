package com.github.brkckr.park.util

import com.google.android.gms.maps.model.LatLng

fun parseTariff(tariff: String?): List<String> {
    return tariff?.split(";")?.map { it.trim() } ?: emptyList()
}

fun parseAreaPolygon(areaPolygon: String?): List<LatLng> {
    val polygonList = mutableListOf<LatLng>()

    try {
        areaPolygon?.replace(("[^\\d., ]").toRegex(), "")?.trim()?.split(",")
            ?.map { polygonString ->
                val latlng = polygonString.trim().split(" ")
                polygonList.add(LatLng(latlng[1].toDouble(), latlng[0].toDouble()))
            }
    } catch (e: Exception) {

    }

    return polygonList.toList()
}

fun parseDistance(distance: Double) : String {
    return if(distance < 1000) distance.toInt().toString()+" m"
           else String.format("%.1f", distance/1000)+" km"
}
