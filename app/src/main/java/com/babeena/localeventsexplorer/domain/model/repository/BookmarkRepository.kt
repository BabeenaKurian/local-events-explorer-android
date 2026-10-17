package com.babeena.localeventsexplorer.domain.model.repository

import com.babeena.localeventsexplorer.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun addBookmark(event: Event)

    suspend fun removeBookmark(eventId: String)

    fun getAllBookmarks(): Flow<List<Event>>

    fun isBookmarked(eventId: String): Flow<Boolean>
}