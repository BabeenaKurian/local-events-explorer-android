
package com.babeena.localeventsexplorerandroidapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity?

    @Query("DELETE FROM events")
    suspend fun clearEvents()

    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    suspend fun getAllEventsOnce(): List<EventEntity>

    @Query("SELECT COUNT(*) FROM events")
    suspend fun getEventCount(): Int
}
