package com.babeena.localeventsexplorer.domain.model.repository

import com.babeena.localeventsexplorer.domain.model.Event

interface EventRepository {

    suspend fun getEvents(): List<Event>

    suspend fun getEventById(id: String): Event

}