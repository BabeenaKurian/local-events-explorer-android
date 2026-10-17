
package com.babeena.localeventsexplorer.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.babeena.localeventsexplorerandroidapp.data.local.AppDatabase
import com.babeena.localeventsexplorerandroidapp.data.local.BookmarkedEventDao
import com.babeena.localeventsexplorerandroidapp.data.local.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `events` (
                `id` TEXT NOT NULL,
                `title` TEXT NOT NULL,
                `location` TEXT NOT NULL,
                `dateTime` TEXT NOT NULL,
                `imageUrl` TEXT,
                `latitude` REAL,
                `longitude` REAL,
                `description` TEXT,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "local_events_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideBookmarkedEventDao(
        database: AppDatabase
    ): BookmarkedEventDao {
        return database.bookmarkedEventDao()
    }

    @Provides
    fun provideEventDao(
        database: AppDatabase
    ): EventDao {
        return database.eventDao()
    }

}
