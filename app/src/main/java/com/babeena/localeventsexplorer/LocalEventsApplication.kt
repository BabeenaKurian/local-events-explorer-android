package com.babeena.localeventsexplorer
import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.babeena.localeventsexplorer.worker.EventRefreshWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class LocalEventsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        // Set this as Coil's default ImageLoader.
        coil.Coil.setImageLoader(imageLoader)

        val refreshWorkRequest =
            PeriodicWorkRequestBuilder<EventRefreshWorker>(
                6, TimeUnit.HOURS
            ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "event_background_refresh",
            ExistingPeriodicWorkPolicy.KEEP,
            refreshWorkRequest
        )

        /*    val testWorkRequest =
                androidx.work.OneTimeWorkRequestBuilder<EventRefreshWorker>()
                    .build()

            WorkManager.getInstance(this).enqueue(testWorkRequest)
       */ }
}
