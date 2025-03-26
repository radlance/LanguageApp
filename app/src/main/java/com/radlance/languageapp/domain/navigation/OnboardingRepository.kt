package com.radlance.languageapp.domain.navigation

import kotlinx.coroutines.flow.Flow

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

interface OnboardingRepository {

    suspend fun savePosition(position: Int)

    fun getPosition(): Flow<Int?>

    suspend fun saveOnboardingViewed(viewed: Boolean)

    fun getOnboardingViewed(): Flow<Boolean>

}