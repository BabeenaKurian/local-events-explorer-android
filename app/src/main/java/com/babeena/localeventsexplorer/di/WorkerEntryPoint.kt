
package com.babeena.localeventsexplorer.di

import com.babeena.localeventsexplorer.data.repository.EventRepositoryImpl
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WorkerEntryPoint {
    fun eventRepository(): EventRepositoryImpl
}
