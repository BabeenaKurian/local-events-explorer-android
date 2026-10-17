
package com.babeena.localeventsexplorer.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.babeena.localeventsexplorer.data.repository.EventRepositoryImpl
import com.babeena.localeventsexplorer.di.WorkerEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CancellationException
import kotlin.jvm.java

class EventRefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val entryPoint = EntryPointAccessors.fromApplication(
                applicationContext,
                WorkerEntryPoint::class.java
            )

            val eventRepository: EventRepositoryImpl = entryPoint.eventRepository()
            Log.d("EventRefreshWorker", "Background event refresh started")
            eventRepository.getEvents()
            Log.d("EventRefreshWorker", "Background event refresh completed")
            Result.success()
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Result.retry()
        }
    }
}
