package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.profile.RemoteProfileRepository
import com.radlance.languageapp.domain.profile.ProfileRepository
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
interface ProfileModule {

    @Binds
    fun provideProfileRepository(profileRepository: RemoteProfileRepository): ProfileRepository
}