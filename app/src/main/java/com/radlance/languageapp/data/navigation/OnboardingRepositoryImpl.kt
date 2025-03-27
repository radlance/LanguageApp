package com.radlance.languageapp.data.navigation

import com.radlance.languageapp.data.core.DataStoreManager
import com.radlance.languageapp.domain.navigation.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

class OnboardingRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : OnboardingRepository {

    override suspend fun savePosition(position: Int) = dataStoreManager.savePosition(position)

    override fun getPosition(): Flow<Int?> = dataStoreManager.getPosition()

    override suspend fun saveOnboardingViewed(viewed: Boolean) {
        dataStoreManager.saveOnboardingViewed(viewed)
    }

    override fun getOnboardingViewed(): Flow<Boolean> = dataStoreManager.getOnboardingViewed()

}