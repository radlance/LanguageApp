package com.radlance.languageapp.presentation.main

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.main.MainFetchContent
import com.radlance.languageapp.domain.main.MainRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _mainContentResultUiState =
        MutableStateFlow<FetchResultUiState<MainFetchContent>>(FetchResultUiState.Initial())
    val mainContentResultUiState: StateFlow<FetchResultUiState<MainFetchContent>> =
        _mainContentResultUiState.onStart {
            fetchMainContent()
        }.stateInViewModel(initialValue = FetchResultUiState.Initial())

    fun fetchMainContent() {
        _mainContentResultUiState.value = FetchResultUiState.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            val mainFetchContent = mainRepository.fetchContent()

            withContext(Dispatchers.Main) {
                _mainContentResultUiState.value = mainFetchContent.map(FetchResultMapper())
            }
        }
    }
}