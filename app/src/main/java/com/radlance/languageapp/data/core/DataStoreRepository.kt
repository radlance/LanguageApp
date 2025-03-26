package com.radlance.languageapp.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

interface DataStoreRepository {

    suspend fun savePosition(position: Int)

    fun getPosition(): Flow<Int?>

    class Base @Inject constructor(
        private val dataStore: DataStore<Preferences>
    ) : DataStoreRepository {

        override suspend fun savePosition(position: Int) {
            dataStore.edit { settings -> settings[KEY_ONBOARDING_POSITION] = position }
        }

        override fun getPosition(): Flow<Int?> {
            return dataStore.data.map { preferences -> preferences[KEY_ONBOARDING_POSITION] }
        }

        companion object {
            val KEY_ONBOARDING_POSITION = intPreferencesKey("onboarding_position")
        }
    }
}