package com.radlance.languageapp.data.navigation

import com.radlance.languageapp.data.core.DataStoreRepository
import com.radlance.languageapp.domain.navigation.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

class OnboardingRepositoryImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : OnboardingRepository {

    override suspend fun savePosition(position: Int) = dataStoreRepository.savePosition(position)

    override fun getPosition(): Flow<Int?> = dataStoreRepository.getPosition()

    override suspend fun saveOnboardingViewed(viewed: Boolean) {
        dataStoreRepository.saveOnboardingViewed(viewed)
    }

    override fun getOnboardingViewed(): Flow<Boolean> = dataStoreRepository.getOnboardingViewed()

}