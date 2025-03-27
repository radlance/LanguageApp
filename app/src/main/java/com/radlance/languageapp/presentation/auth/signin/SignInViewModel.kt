package com.radlance.languageapp.presentation.auth.signin

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.R
import com.radlance.languageapp.common.ResourceProvider
import com.radlance.languageapp.domain.auth.AuthRepository
import com.radlance.languageapp.domain.auth.AuthResult
import com.radlance.languageapp.domain.auth.User
import com.radlance.languageapp.presentation.auth.common.AuthResultUiState
import com.radlance.languageapp.presentation.auth.common.AuthViewModel
import com.radlance.languageapp.presentation.auth.common.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class SignInViewModel @Inject constructor(
    validator: Validator,
    private val repository: AuthRepository,
    private val mapper: AuthResult.Mapper<AuthResultUiState>,
    private val resourceProvider: ResourceProvider
) : AuthViewModel(validator) {
    private val _signInResultUiState =
        MutableStateFlow<AuthResultUiState>(AuthResultUiState.Initial)
    val signInResultUiState: StateFlow<AuthResultUiState>
        get() = _signInResultUiState.asStateFlow()

    fun signIn(email: String, password: String) {
        validateFields(email = email, password = password)

        with(authUiState.value) {
            if (isCorrectEmail && isCorrectPassword) {
                viewModelScope.launch {
                    val user = User(email = email, password = password)
                    _signInResultUiState.value = AuthResultUiState.Loading(
                        resourceProvider.getString(R.string.loading)
                    )
                    val result = repository.signIn(user)
                    _signInResultUiState.value = result.map(mapper)
                }
            } else {
                authUiStateMutable.update { currentState ->
                    currentState.copy(showErrorDialog = true)
                }
            }
        }
    }
}