package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.game.RemoteGameRepository
import com.radlance.languageapp.domain.game.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
interface GameModule {

    @Binds
    fun provideGameModule(gameRepository: RemoteGameRepository): GameRepository
}