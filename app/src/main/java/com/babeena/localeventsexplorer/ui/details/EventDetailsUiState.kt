package com.babeena.localeventsexplorerandroidapp.ui.details

import com.babeena.localeventsexplorer.domain.model.Event

data class EventDetailsUiState(
    val isLoading: Boolean = false,
    val event: Event? = null,
    val errorMessage: String? = null)