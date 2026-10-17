package com.babeena.localeventsexplorerandroidapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*@Database(
    entities = [BookmarkedEventEntity::class],
    version = 1,
    exportSchema = false
)*/

@Database(
    entities = [
        BookmarkedEventEntity::class,
        EventEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkedEventDao(): BookmarkedEventDao
    abstract fun eventDao(): EventDao

}