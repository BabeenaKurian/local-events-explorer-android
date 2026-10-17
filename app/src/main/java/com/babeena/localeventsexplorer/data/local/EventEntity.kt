
package com.babeena.localeventsexplorerandroidapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val location: String,
    val dateTime: String,
    val imageUrl: String?,
    val latitude: Double?,
    val longitude: Double?,
    val description: String?
)
