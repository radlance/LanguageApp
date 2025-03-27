package com.radlance.languageapp.domain.auth

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface AuthRepository {

    suspend fun signIn(user: User): AuthResult

    suspend fun signUp(user: User): AuthResult

    suspend fun loadUserData(): AuthResult
}