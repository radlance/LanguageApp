package com.radlance.languageapp.data.core

import kotlinx.coroutines.flow.Flow

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

interface DataStoreManager {

    suspend fun savePosition(position: Int)

    fun getPosition(): Flow<Int?>

    suspend fun saveOnboardingViewed(viewed: Boolean)

    fun getOnboardingViewed(): Flow<Boolean>

    suspend fun saveJwt(token: String)

    fun getJwt(): Flow<String?>

    suspend fun deleteToken()
}