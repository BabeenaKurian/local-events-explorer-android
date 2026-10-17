package com.babeena.localeventsexplorer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class LocationHelper(context: Context) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            val lastLocation = fusedLocationClient.lastLocation.await()

            if (lastLocation != null) {
                Log.d("LocationHelper", "Using last location: $lastLocation")
                return lastLocation
            }

            Log.d("LocationHelper", "Last location is null; requesting current location")

            withTimeout(15_000) {
                fusedLocationClient
                    .getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    )
                    .await()
            }.also { location ->
                Log.d("LocationHelper", "Current location result: $location")
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Log.e("LocationHelper", "Location retrieval failed", exception)
            null
        }
    }


}
