
package com.babeena.localeventsexplorerandroidapp.di

import com.babeena.localeventsexplorerandroidapp.data.repository.BookmarkRepositoryImpl
import com.babeena.localeventsexplorerandroidapp.domain.repository.BookmarkRepository
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
