
package com.babeena.localeventsexplorer.di

import com.babeena.localeventsexplorer.data.repository.BookmarkRepositoryImpl
import com.babeena.localeventsexplorer.domain.model.repository.BookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkModule {

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(
        implementation: BookmarkRepositoryImpl
    ): BookmarkRepository
}
