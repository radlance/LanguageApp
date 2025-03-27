package com.radlance.languageapp.data.di

import android.content.Context
import com.radlance.languageapp.common.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context


    @Singleton
    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider.Base(context)
    }
}