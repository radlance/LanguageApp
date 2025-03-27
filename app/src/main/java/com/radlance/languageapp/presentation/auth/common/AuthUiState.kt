package com.radlance.languageapp.presentation.auth.common

data class AuthUiState(
    val isCorrectFirstName: Boolean = true,
    val isCorrectLastName: Boolean = true,
    val isCorrectEmail: Boolean = true,
    val isCorrectPassword: Boolean = true,
    val isCorrectConfirmPassword: Boolean = true,
    val showErrorDialog: Boolean = false
)
