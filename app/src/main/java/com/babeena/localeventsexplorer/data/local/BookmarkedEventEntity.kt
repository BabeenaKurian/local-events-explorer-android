package com.babeena.localeventsexplorerandroidapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_events")
data class BookmarkedEventEntity (@PrimaryKey
                                  val id: String,
                                  val title: String,
                                  val location: String,
                                  val dateTime: String,
                                  val imageUrl: String?,
                                  val latitude: Double?,
                                  val longitude: Double?,
                                  val description: String?
)