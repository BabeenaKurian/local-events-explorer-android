package com.babeena.localeventsexplorerandroidapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsexplorer.data.repository.EventRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor (private val repository: EventRepositoryImpl): ViewModel() {

    private val _eventDetailsUiState = MutableStateFlow(EventDetailsUiState())
    val eventDetailsUiState: StateFlow<EventDetailsUiState> = _eventDetailsUiState

    fun loadEventDetails(eventId: String){
        viewModelScope.launch {
            _eventDetailsUiState.value = _eventDetailsUiState.value.copy(isLoading =true)
            try {
                val result = repository.getEventById(eventId)
                _eventDetailsUiState.value = _eventDetailsUiState.value.copy(isLoading = false, event = result)
            }catch (e: Exception){
                _eventDetailsUiState.value = _eventDetailsUiState.value.copy(isLoading = false, errorMessage = e.message?:"Unable to load event details")
            }
        }
    }


}