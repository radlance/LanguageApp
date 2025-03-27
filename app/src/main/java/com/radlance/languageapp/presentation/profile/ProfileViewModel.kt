package com.radlance.languageapp.presentation.profile

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.domain.profile.ProfileRepository
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
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {
    private val _userDataUiState =
        MutableStateFlow<FetchResultUiState<User>>(FetchResultUiState.Initial())
    val userDataUiState: StateFlow<FetchResultUiState<User>> = _userDataUiState.onStart {
        loadUserData()
    }.stateInViewModel(initialValue = FetchResultUiState.Initial())

    fun loadUserData() {
        _userDataUiState.value = FetchResultUiState.Loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            val result = profileRepository.loadUserInfo()

            withContext(Dispatchers.Main) {
                _userDataUiState.value = result.map(FetchResultMapper())
            }
        }
    }
}