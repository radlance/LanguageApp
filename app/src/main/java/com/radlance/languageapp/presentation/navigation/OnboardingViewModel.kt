package com.radlance.languageapp.presentation.navigation

import androidx.lifecycle.ViewModel
import com.radlance.languageapp.presentation.onboarding.OnboardingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Stack

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

class OnboardingViewModel : ViewModel() {
    private val screenStack = Stack<OnboardingState>().apply {
        addAll(listOf(OnboardingState.Last, OnboardingState.Second, OnboardingState.First))
    }

    private val _onboardingScreenState = MutableStateFlow<OnboardingState>(screenStack.pop())

    val onboardingState: StateFlow<OnboardingState>
        get() = _onboardingScreenState.asStateFlow()

    fun moveToNextScreen() {
        _onboardingScreenState.value = screenStack.pop()
    }
}