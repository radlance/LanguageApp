package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.auth.RemoteAuthRepository
import com.radlance.languageapp.domain.auth.AuthRepository
import com.radlance.languageapp.domain.auth.AuthResult
import com.radlance.languageapp.presentation.auth.common.AuthResultMapper
import com.radlance.languageapp.presentation.auth.common.AuthResultUiState
import com.radlance.languageapp.presentation.auth.common.Validator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun provideValidator(validator: Validator.Base): Validator

    @Binds
    fun provideAuthRepository(authRepository: RemoteAuthRepository): AuthRepository

    @Binds
    fun provideMapper(authResult: AuthResultMapper): AuthResult.Mapper<AuthResultUiState>
}