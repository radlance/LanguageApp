package com.radlance.languageapp.presentation.auth.common

import com.radlance.languageapp.domain.auth.AuthResult
import javax.inject.Inject

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class AuthResultMapper @Inject constructor() : AuthResult.Mapper<AuthResultUiState> {

    override fun mapSuccess(): AuthResultUiState {
        return AuthResultUiState.Success
    }

    override fun mapError(message: String): AuthResultUiState {
        return AuthResultUiState.Error(message)
    }
}