package com.radlance.languageapp.data.di

import com.radlance.languageapp.data.animal.RemoteAnimalRepository
import com.radlance.languageapp.domain.animal.AnimalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

@Module
@InstallIn(SingletonComponent::class)
interface AnimalModule {

    @Binds
    fun provideAnimalRepository(animalRepository: RemoteAnimalRepository): AnimalRepository
}