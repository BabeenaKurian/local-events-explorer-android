package com.babeena.localeventsexplorer.data.repository

import com.babeena.localeventsexplorer.data.remote.EventApiService
import com.babeena.localeventsexplorer.domain.model.Event
import com.babeena.localeventsexplorer.domain.model.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (
    private val eventApiService: EventApiService): EventRepository {
    override suspend fun getEvents(): List<Event> {
        return eventApiService.getEvents()
    }

    override suspend fun getEventById(id: String): Event {
        return eventApiService.getEventById(id)
    }
}