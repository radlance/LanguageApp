package com.radlance.languageapp.presentation.common

import com.radlance.languageapp.domain.remote.FetchResult

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

class FetchResultMapper<T : Any> : FetchResult.Mapper<T, FetchResultUiState<T>> {

    override fun mapSuccess(data: T): FetchResultUiState<T> {
        return FetchResultUiState.Success(data)
    }

    override fun mapError(data: T?): FetchResultUiState<T> {
        return FetchResultUiState.Error(data)
    }
}