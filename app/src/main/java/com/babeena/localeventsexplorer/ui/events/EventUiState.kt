package com.babeena.localeventsexplorer.ui.events

import com.babeena.localeventsexplorer.domain.model.Event

data class EventUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val errorMessage: String? = null
)
