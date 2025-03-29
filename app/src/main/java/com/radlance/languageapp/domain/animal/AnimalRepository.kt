package com.radlance.languageapp.domain.animal

import com.radlance.languageapp.domain.remote.FetchResult

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

interface AnimalRepository {

    suspend fun animals(): FetchResult<List<Animal>>
}