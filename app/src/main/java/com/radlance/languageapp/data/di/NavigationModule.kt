package com.radlance.languageapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.radlance.languageapp.data.core.DataStoreRepository
import com.radlance.languageapp.data.navigation.OnboardingRepositoryImpl
import com.radlance.languageapp.domain.navigation.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepository.Base(dataStore)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(dataStoreRepository: DataStoreRepository): OnboardingRepository {
        return OnboardingRepositoryImpl(dataStoreRepository)
    }

    private companion object {
        val Context.dataStore by preferencesDataStore("settings")
    }
}