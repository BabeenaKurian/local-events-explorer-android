package com.babeena.localeventsexplorer.ui.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.babeena.localeventsexplorer.ui.theme.primaryRed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import coil.compose.AsyncImage
import com.babeena.localeventsexplorer.utils.EventDateUtils.formatEventDate

@Composable
fun eventScreen(viewModel: EventViewModel) {

    val eventUiState by viewModel.eventUiState.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                // Screen header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 20.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Events List",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                when {
                    eventUiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    eventUiState.errorMessage != null -> {
                        Column() {
                            Text(text = eventUiState.errorMessage ?: "Please try again!!")
                        }
                    }

                    else -> {
                        if (eventUiState.events != null) {
                            val events = eventUiState.events

                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = events,
                                    key = { event -> event.id }
                                ) { event ->

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 16.dp,
                                                vertical = 8.dp
                                            ),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor =
                                                MaterialTheme.colorScheme.surface
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 3.dp
                                        )
                                    ) {
                                        Column {
                                            AsyncImage(
                                                model = event.imageUrl,
                                                contentDescription = event.title,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 20.dp,
                                                            topEnd = 20.dp
                                                        )
                                                    ),
                                                contentScale = ContentScale.Crop
                                            )

                                            Column(
                                                modifier = Modifier.padding(16.dp),
                                                verticalArrangement =
                                                    Arrangement.spacedBy(10.dp)
                                            ) {

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = event.title,
                                                        modifier = Modifier.weight(1f),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment =
                                                        Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector =
                                                            Icons.Default.CalendarMonth,
                                                        contentDescription = null,
                                                        tint = primaryRed,
                                                        modifier = Modifier.size(20.dp)
                                                    )

                                                    Text(
                                                        text = formatEventDate(
                                                            event.dateTime
                                                        ),
                                                        modifier = Modifier.padding(
                                                            start = 8.dp
                                                        ),
                                                        style = MaterialTheme.typography
                                                            .bodyMedium,
                                                        color = MaterialTheme.colorScheme
                                                            .onSurfaceVariant
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment =
                                                        Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector =
                                                            Icons.Default.LocationOn,
                                                        contentDescription = null,
                                                        tint = primaryRed,
                                                        modifier = Modifier.size(20.dp),

                                                        )

                                                    Text(
                                                        text = event.location,
                                                        modifier = Modifier.padding(
                                                            start = 8.dp
                                                        ),
                                                        style = MaterialTheme.typography
                                                            .bodyMedium,
                                                        color = MaterialTheme.colorScheme
                                                            .onSurfaceVariant
                                                    )
                                                }
                                                event.description?.let {
                                                    Text(
                                                        text = it,
                                                        modifier = Modifier.padding(
                                                            top = 2.dp
                                                        ),
                                                        style = MaterialTheme.typography
                                                            .bodyMedium,
                                                        color = MaterialTheme.colorScheme
                                                            .onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Column() {
                                Text("No events available")
                            }
                        }
                    }
                }
            }
        }
    }

}