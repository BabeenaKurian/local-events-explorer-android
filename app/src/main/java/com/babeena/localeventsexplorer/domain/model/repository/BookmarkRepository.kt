package com.babeena.localeventsexplorerandroidapp.domain.repository

import com.babeena.localeventsexplorerandroidapp.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun addBookmark(event: Event)

    suspend fun removeBookmark(eventId: String)

    fun getAllBookmarks(): Flow<List<Event>>

    fun isBookmarked(eventId: String): Flow<Boolean>
}