package com.radlance.languageapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.radlance.languageapp.data.core.BaseDataStoreManager
import com.radlance.languageapp.data.core.DataStoreManager
import com.radlance.languageapp.data.navigation.OnboardingRepositoryImpl
import com.radlance.languageapp.domain.navigation.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(dataStore: DataStore<Preferences>): DataStoreManager {
        return BaseDataStoreManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(dataStoreManager: DataStoreManager): OnboardingRepository {
        return OnboardingRepositoryImpl(dataStoreManager)
    }

    private companion object {
        val Context.dataStore by preferencesDataStore("settings")
    }
}