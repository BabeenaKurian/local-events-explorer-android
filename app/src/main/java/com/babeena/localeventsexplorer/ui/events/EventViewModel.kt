package com.babeena.localeventsexplorer.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babeena.localeventsexplorer.data.repository.EventRepositoryImpl
import com.babeena.localeventsexplorer.domain.model.Event
import com.babeena.localeventsexplorer.domain.model.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepositoryImpl,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState

    private val _bookmarkedEventIds = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedEventIds: StateFlow<Set<String>> = _bookmarkedEventIds

    fun getEvents() {
        viewModelScope.launch {
            _eventUiState.value = _eventUiState.value.copy(isLoading = true)
            try {
                val result = eventRepository.getEvents()
                _eventUiState.value = _eventUiState.value.copy(isLoading = false, events = result)
            } catch (e: Exception) {
                _eventUiState.value = _eventUiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unable to load events..."
                )
            }
        }
    }


    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkRepository.getAllBookmarks().collect { bookmarks ->
                _bookmarkedEventIds.value = bookmarks.map { it.id }.toSet()
            }
        }
    }


    fun addBookmark(event: Event) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(event)
        }
    }

    fun removeBookmark(eventId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(eventId)
        }
    }


    fun toggleBookmark(event: Event) {
        if (event.id in _bookmarkedEventIds.value) {
            removeBookmark(event.id)
        } else {
            addBookmark(event)
        }
    }

    init {
        getEvents()
        observeBookmarks()
    }

}