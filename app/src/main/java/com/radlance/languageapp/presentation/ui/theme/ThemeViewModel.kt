package com.radlance.languageapp.presentation.ui.theme

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.theme.ThemeRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : BaseViewModel() {
    val isDarkUserTheme = themeRepository.isDarkUserTheme().stateInViewModel(initialValue = null)

    fun changeTheme(isDark: Boolean) {
        viewModelScope.launch {
            themeRepository.changeTheme(isDark)
        }
    }
}