package com.radlance.languageapp.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.navigation.OnboardingRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.onboarding.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.ArrayDeque
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : BaseViewModel() {

    val onboardingViewed = onboardingRepository.getOnboardingViewed().stateInViewModel(
        initialValue = false
    )

    private val screens = listOf(
        OnboardingState.First,
        OnboardingState.Second,
        OnboardingState.Last
    )

    private val screenQueue = ArrayDeque<OnboardingState>()

    private val _onboardingScreenState = MutableStateFlow<OnboardingState?>(null)

    val onboardingState: StateFlow<OnboardingState?> = _onboardingScreenState.onStart {
        screenQueue.addAll(
            onboardingRepository.getPosition().first()?.let { screens.drop(it) } ?: screens
        )

        moveToNextScreen()
    }.stateInViewModel(initialValue = null)

    fun moveToNextScreen() {
        val currentScreen = screenQueue.peek()
        currentScreen?.let {
            viewModelScope.launch {
                val position = screens.indexOf(currentScreen)
                onboardingRepository.savePosition(position)
            }
            _onboardingScreenState.value = screenQueue.pop()
        }
    }

    fun saveOnboardingViewed() {
        viewModelScope.launch {
            onboardingRepository.saveOnboardingViewed(viewed = true)
        }
    }
}