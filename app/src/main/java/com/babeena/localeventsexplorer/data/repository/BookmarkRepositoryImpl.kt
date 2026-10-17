
package com.babeena.localeventsexplorerandroidapp.data.repository

import com.babeena.localeventsexplorerandroidapp.data.local.BookmarkedEventDao
import com.babeena.localeventsexplorerandroidapp.data.local.BookmarkedEventEntity
import com.babeena.localeventsexplorerandroidapp.domain.model.Event
import com.babeena.localeventsexplorerandroidapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkedEventDao
) : BookmarkRepository {

    override suspend fun addBookmark(event: Event) {
        bookmarkDao.insertBookmark(event.toEntity())
    }

    override suspend fun removeBookmark(eventId: String) {
        bookmarkDao.removeBookmark(eventId)
    }

    override fun getAllBookmarks(): Flow<List<Event>> {
        return bookmarkDao.getAllBookmarks().map { entities ->
            entities.map { it.toEvent() }
        }
    }

    override fun isBookmarked(eventId: String): Flow<Boolean> {
        return bookmarkDao.isBookmarked(eventId)
    }

    private fun Event.toEntity(): BookmarkedEventEntity {
        return BookmarkedEventEntity(
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

    private fun BookmarkedEventEntity.toEvent(): Event {
        return Event(
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
}
