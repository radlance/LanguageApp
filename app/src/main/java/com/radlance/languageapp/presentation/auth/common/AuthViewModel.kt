package com.radlance.languageapp.presentation.auth.common

import com.radlance.languageapp.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

abstract class AuthViewModel(private val validator: Validator) : BaseViewModel() {
    protected val authUiStateMutable = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState>
        get() = authUiStateMutable.asStateFlow()


    protected fun validateFields(
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        password: String? = null,
        confirmPassword: String? = null
    ) {
        authUiStateMutable.update { currentState ->
            currentState.copy(
                isCorrectFirstName = firstName?.let { validator.validFirstName(it) } ?: true,
                isCorrectLastName = lastName?.let { validator.validFirstName(it) } ?: true,
                isCorrectEmail = email?.let { validator.validEmail(it) } ?: true,
                isCorrectPassword = password?.let { validator.validPassword(password) } ?: true,
                isCorrectConfirmPassword = confirmPassword?.let { password == confirmPassword }
                    ?: true
            )
        }
    }

    fun resetFirstName() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectEmail = true)
        }
    }

    fun resetLastNameError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectEmail = true)
        }
    }

    fun resetEmailError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectEmail = true)
        }
    }

    fun resetPasswordError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectPassword = true)
        }
    }

    fun resetConfirmPasswordError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectPassword = true)
        }
    }

    fun resetDialogError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(showErrorDialog = false)
        }
    }
}