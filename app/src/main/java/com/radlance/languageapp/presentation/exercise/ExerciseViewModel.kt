package com.radlance.languageapp.presentation.exercise

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.profile.ProfileRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Дата создания: 30.03.2025
 * Автор: Манякин Дмитрий
 */

abstract class ExerciseViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {
    protected val incrementResultUiStateMutable =
        MutableStateFlow<FetchResultUiState<Unit>>(FetchResultUiState.Initial())
    val incrementResultUiState: StateFlow<FetchResultUiState<Unit>>
        get() = incrementResultUiStateMutable.asStateFlow()

    private var currentStreak = 0.0

    fun resetScoreState() {
        incrementResultUiStateMutable.value = FetchResultUiState.Initial()
    }

    fun resetStreak() {
        currentStreak = 0.0
    }

    fun updateCurrentStreak() {
        currentStreak++
    }

    fun incrementUserScore() {
        var currentScore = 1.0

        if (currentStreak >= 2) {
            currentScore += 0.2 * currentStreak
        }

        incrementResultUiStateMutable.value = FetchResultUiState.Loading(null)

        viewModelScope.launch(Dispatchers.IO) {

            val result = profileRepository.updateUserScore(currentScore)

            withContext(Dispatchers.Main) {
                incrementResultUiStateMutable.value = result.map(FetchResultMapper())
            }
        }
    }
}