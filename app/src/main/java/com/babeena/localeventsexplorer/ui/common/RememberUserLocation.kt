package com.babeena.localeventsexplorer.ui.common

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.babeena.localeventsexplorer.utils.LocationHelper
import com.babeena.localeventsexplorer.utils.LocationPermissionHelper
import kotlinx.coroutines.launch

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)

@Composable
fun rememberUserLocation(): UserLocation? {
    val context = LocalContext.current
    var userLocation by remember {
        mutableStateOf<UserLocation?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    val locationHelper = remember {
        LocationHelper(context)
    }

    val fetchLocation: () -> Unit = {
        coroutineScope.launch {
            val location = locationHelper.getCurrentLocation()
            if (location != null) {
                userLocation = UserLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }
    }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                        permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                fetchLocation()
            }
        }

    LaunchedEffect(Unit) {
        if (LocationPermissionHelper.hasLocationPermission(context)) {
            fetchLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    return userLocation
}
