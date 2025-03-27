package com.radlance.languageapp.presentation.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class SignUpViewModel @Inject constructor(
    validator: Validator,
    private val repository: AuthRepository,
    private val mapper: AuthResult.Mapper<AuthResultUiState>,
    private val resourceProvider: ResourceProvider
) : AuthViewModel(validator) {
    private val _signUpResultUiState =
        MutableStateFlow<AuthResultUiState>(AuthResultUiState.Initial)
    val signUpResultUiState: StateFlow<AuthResultUiState>
        get() = _signUpResultUiState.asStateFlow()

    var navigateToLastSignUpScreen by mutableStateOf(false)
        private set

    fun resetNavigationState() {
        navigateToLastSignUpScreen = false
    }

    fun navigateToLastSignUpScreen(firstName: String, lastName: String, email: String) {
        validateFields(
            firstName = firstName,
            lastName = lastName,
            email = email
        )

        with(authUiState.value) {
            if (isCorrectFirstName && isCorrectLastName && isCorrectEmail) {
                navigateToLastSignUpScreen = true
            }
        }
    }

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        validateFields(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

        with(authUiState.value) {
            if (isCorrectEmail && isCorrectPassword && isCorrectFirstName && isCorrectLastName && isCorrectConfirmPassword) {
                viewModelScope.launch {
                    val user = User(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password
                    )
                    _signUpResultUiState.value = AuthResultUiState.Loading(
                        resourceProvider.getString(R.string.loading)
                    )
                    val result = repository.signUp(user)
                    _signUpResultUiState.value = result.map(mapper)
                }
            } else {
                authUiStateMutable.update { currentState ->
                    currentState.copy(showErrorDialog = true)
                }
            }
        }

    }
}