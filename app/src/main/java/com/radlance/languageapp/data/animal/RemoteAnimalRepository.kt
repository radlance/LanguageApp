package com.radlance.languageapp.data.animal

import android.util.Log
import com.radlance.languageapp.data.api.core.AppService
import com.radlance.languageapp.data.api.core.RemoteMapper
import com.radlance.languageapp.domain.animal.Animal
import com.radlance.languageapp.domain.animal.AnimalRepository
import com.radlance.languageapp.domain.remote.FetchResult
import javax.inject.Inject

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

class RemoteAnimalRepository @Inject constructor(
    private val appService: AppService
) : AnimalRepository, RemoteMapper() {

    override suspend fun animals(): FetchResult<List<Animal>> {
        return try {
            val animals = appService.animals().map { dto ->
                dto.toAnimal(dto.image?.let { appService.animalByFileName(it) })
            }

            FetchResult.Success(animals)
        } catch (e: Exception) {
            Log.d("RemoteAnimalRepository", e.message!!)
            FetchResult.Error(null)
        }
    }
}