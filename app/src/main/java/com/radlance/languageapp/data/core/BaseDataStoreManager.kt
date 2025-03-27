package com.radlance.languageapp.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BaseDataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {

    override suspend fun savePosition(position: Int) {
        dataStore.edit { settings -> settings[KEY_ONBOARDING_POSITION] = position }
    }

    override fun getPosition(): Flow<Int?> {
        return dataStore.data.map { preferences -> preferences[KEY_ONBOARDING_POSITION] }
    }

    override suspend fun saveOnboardingViewed(viewed: Boolean) {
        dataStore.edit { settings -> settings[KEY_ONBOARDING_VIEWED] = viewed }
    }

    override fun getOnboardingViewed(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[KEY_ONBOARDING_VIEWED] ?: false }
    }

    override suspend fun saveJwt(token: String) {
        dataStore.edit { settings -> settings[KEY_JWT] = token }
    }

    override fun getJwt(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[KEY_JWT] }

    }

    override suspend fun deleteToken() {
        dataStore.edit { preferences -> preferences.remove(KEY_JWT) }
    }

    companion object {
        val KEY_ONBOARDING_POSITION = intPreferencesKey("onboarding_position")
        val KEY_ONBOARDING_VIEWED = booleanPreferencesKey("onboarding_viewed")
        val KEY_JWT = stringPreferencesKey("jwt")
    }
}