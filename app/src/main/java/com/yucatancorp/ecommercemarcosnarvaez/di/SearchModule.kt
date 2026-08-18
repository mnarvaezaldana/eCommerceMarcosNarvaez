package com.yucatancorp.ecommercemarcosnarvaez.di

import android.content.Context
import com.yucatancorp.ecommercemarcosnarvaez.domain.SearchHistoryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchHistoryManager(
        @ApplicationContext context: Context
    ): SearchHistoryManager {
        return SearchHistoryManager(context)
    }
}