package com.babeena.localeventsexplorerandroidapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkedEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(event: BookmarkedEventEntity)

    @Query("DELETE FROM bookmarked_events WHERE id = :eventId")
    suspend fun removeBookmark(eventId: String)

    @Query("SELECT * FROM bookmarked_events ORDER BY title ASC")
    fun getAllBookmarks(): Flow<List<BookmarkedEventEntity>>

    @Query(
        "SELECT EXISTS(" +
                "SELECT 1 FROM bookmarked_events WHERE id = :eventId" +
                ")"
    )

    fun isBookmarked(eventId: String): Flow<Boolean>

}