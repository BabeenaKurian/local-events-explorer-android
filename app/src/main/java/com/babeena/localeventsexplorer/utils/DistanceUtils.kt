package com.babeena.localeventsexplorer.utils

import android.location.Location

object DistanceUtils {

    fun calculateDistanceInKm(
        userLatitude: Double,
        userLongitude: Double,
        eventLatitude: Double?,
        eventLongitude: Double?
    ): Double? {
        if (eventLatitude == null || eventLongitude == null || userLatitude ==null || userLongitude==null) {
            return null
        }

        val results = FloatArray(1)

        Location.distanceBetween(
            userLatitude,
            userLongitude,
            eventLatitude,
            eventLongitude,
            results
        )

        return results[0] / 1000.0
    }
}
