
package com.babeena.localeventsexplorerandroidapp.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.babeena.localeventsexplorer.ui.common.rememberUserLocation
import com.babeena.localeventsexplorer.ui.theme.primaryRed
import com.babeena.localeventsexplorer.utils.DistanceUtils
import com.babeena.localeventsexplorer.utils.EventDateUtils.formatEventDate


@Composable
fun EventDetailsScreen(
    viewModel: EventDetailsViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.eventDetailsUiState
        .collectAsStateWithLifecycle()

    val userLocation = rememberUserLocation()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 4.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }

                Text(
                    text = "Event Details",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { innerPadding ->

        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                    Text("Loading event details...")
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.errorMessage
                            ?: "Unable to load event details.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            uiState.event != null -> {
                val event = uiState.event!!

                val distanceInKm = userLocation?.let { location ->
                    DistanceUtils.calculateDistanceInKm(
                        userLatitude = location.latitude,
                        userLongitude = location.longitude,
                        eventLatitude = event.latitude,
                        eventLongitude = event.longitude
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = event.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = primaryRed
                        )

                        Text(
                            text = formatEventDate(event.dateTime),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = primaryRed
                        )

                        Text(
                            text = event.location,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (distanceInKm != null) {
                        Text(
                            text = String.format(
                                java.util.Locale.getDefault(),
                                "%.1f km away",
                                distanceInKm
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = {
                            val mapUri = Uri.parse(
                                "geo:${event.latitude},${event.longitude}?q=${event.latitude},${event.longitude}(${
                                    Uri.encode(
                                        event.title
                                    )
                                })"
                            )
                            val mapIntent =
                                Intent(Intent.ACTION_VIEW, mapUri)
                            context.startActivity(mapIntent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE41B1F),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Open in Maps")
                    }
                    HorizontalDivider()

                    Text(
                        text = "About this event",
                        style = MaterialTheme.typography.titleLarge
                    )

                    event.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                        )
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No event details available.")
                }
            }
        }
    }
}
