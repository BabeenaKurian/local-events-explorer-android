
package com.babeena.localeventsexplorer.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheTimestampStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.getSharedPreferences(
        "event_cache_preferences",
        Context.MODE_PRIVATE
    )

    fun getLastFetchTime(): Long {
        return preferences.getLong(KEY_LAST_FETCH_TIME, 0L)
    }

    fun saveLastFetchTime(timestamp: Long) {
        preferences.edit()
            .putLong(KEY_LAST_FETCH_TIME, timestamp)
            .apply()
    }

    companion object {
        private const val KEY_LAST_FETCH_TIME = "last_fetch_time"
    }
}
