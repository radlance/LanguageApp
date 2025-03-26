package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.language.LanguageRepositoryImpl
import com.radlance.languageapp.domain.language.LanguageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Дата создания: 26.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
interface LanguageModule {

    @Binds
    fun provideLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl): LanguageRepository
}