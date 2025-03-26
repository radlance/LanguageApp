package com.radlance.languageapp.presentation.language

import com.radlance.languageapp.domain.language.Language
import com.radlance.languageapp.domain.language.LanguageRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
) : BaseViewModel() {
    private val _languages = MutableStateFlow<List<Language>>(emptyList())

    val languages: StateFlow<List<Language>> = _languages.onStart {
        loadLanguages()
    }.stateInViewModel(emptyList())

    fun loadLanguages() {
        _languages.value = languageRepository.loadLanguages()
    }
}