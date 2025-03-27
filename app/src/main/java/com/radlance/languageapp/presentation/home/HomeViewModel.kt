package com.radlance.languageapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.auth.AuthRepository
import com.radlance.languageapp.domain.auth.AuthResult
import com.radlance.languageapp.presentation.auth.common.AuthResultUiState
import com.radlance.languageapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authMapper: AuthResult.Mapper<AuthResultUiState>
) : BaseViewModel() {
    private val _userInfo = MutableStateFlow<AuthResultUiState>(AuthResultUiState.Initial)
    val userInfo: StateFlow<AuthResultUiState> =
        _userInfo.onStart { loadUserInfo() }
            .stateInViewModel(initialValue = AuthResultUiState.Initial)

    fun loadUserInfo() {
        viewModelScope.launch {
            _userInfo.value = authRepository.loadUserData().map(authMapper)
        }
    }
}