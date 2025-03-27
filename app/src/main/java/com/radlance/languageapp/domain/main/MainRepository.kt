package com.radlance.languageapp.domain.main

import com.radlance.languageapp.domain.remote.FetchResult

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface MainRepository {

    suspend fun fetchContent(): FetchResult<MainFetchContent>

}