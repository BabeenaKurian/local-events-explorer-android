package com.babeena.localeventsexplorer.data.repository

import android.util.Log
import com.babeena.localeventsexplorer.data.remote.EventApiService
import com.babeena.localeventsexplorer.domain.model.Event
import com.babeena.localeventsexplorer.domain.model.repository.EventRepository
import com.babeena.localeventsexplorerandroidapp.data.local.EventDao
import com.babeena.localeventsexplorerandroidapp.data.local.EventEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (
    private val eventApiService: EventApiService,
    private val eventDao: EventDao,
    private val cacheTimestampStore: CacheTimestampStore): EventRepository {
    override suspend fun getEvents(): List<Event> {

        Log.d("EventRepository", "getEvents() called")

        val cacheAge = System.currentTimeMillis() -
                cacheTimestampStore.getLastFetchTime()

        val cacheIsFresh = cacheTimestampStore.getLastFetchTime() > 0L &&
                cacheAge < TimeUnit.MINUTES.toMillis(5)

        if (cacheIsFresh) {
            val cachedEvents = eventDao.getAllEventsOnce()
            if (cachedEvents.isNotEmpty()) {
                Log.d("EventRepository", "Returning events from Room cache")
                return cachedEvents.map { it.toEvent() }
            }
        }

        return try {
            Log.d("EventRepository", "Making GET /events network request")
            val events = eventApiService.getEvents()

            eventDao.insertEvents(events.map { it.toEntity() })
            cacheTimestampStore.saveLastFetchTime(System.currentTimeMillis())

            events
        } catch (exception: Exception) {
            val cachedEvents = eventDao.getAllEventsOnce()

            if (cachedEvents.isNotEmpty()) {
                cachedEvents.map { it.toEvent() }
            } else {
                throw exception
            }
        }
    }



    override suspend fun getEventById(id: String): Event {
        return try {
            eventApiService.getEventById(id)
        } catch (exception: Exception) {
            eventDao.getEventById(id)?.toEvent()
                ?: throw exception
        }
    }

    private fun Event.toEntity() = EventEntity(
        id = id,
        title = title,
        location = location,
        dateTime = dateTime,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        description = description
    )

    private fun EventEntity.toEvent() = Event(
        id = id,
        title = title,
        location = location,
        dateTime = dateTime,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        description = description
    )
}