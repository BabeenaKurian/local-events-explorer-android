package com.babeena.localeventsexplorer.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsexplorer.data.repository.EventRepositoryImpl
import com.babeena.localeventsexplorer.domain.model.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: EventRepositoryImpl): ViewModel() {

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState

    fun getEvents(){
        viewModelScope.launch{
            _eventUiState.value = _eventUiState.value.copy(isLoading = true)
            try{
                val result = eventRepository.getEvents()
                _eventUiState.value = _eventUiState.value.copy(isLoading = false, events = result)
            }catch (e: Exception){
                _eventUiState.value=_eventUiState.value.copy(isLoading = false, errorMessage = e.message?:"Unable to load events...")
            }
        }
    }

    init {
        getEvents()
    }

}