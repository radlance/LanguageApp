package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.theme.SystemThemeRepository
import com.radlance.languageapp.domain.theme.ThemeRepository
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
interface ThemeModule {

    @Binds
    fun provideThemeRepository(themeRepository: SystemThemeRepository): ThemeRepository
}