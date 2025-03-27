package com.radlance.languageapp.presentation.common

import androidx.compose.runtime.Composable

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface FetchResultUiState<T> {

    @Composable
    fun Show(
        onSuccess: @Composable (T) -> Unit,
        onError: @Composable (T?) -> Unit,
        onLoading: @Composable (T?) -> Unit,
    )

    class Success<T : Any>(private val data: T) : FetchResultUiState<T> {
        @Composable
        override fun Show(
            onSuccess: @Composable (T) -> Unit,
            onError: @Composable (T?) -> Unit,
            onLoading: @Composable (T?) -> Unit
        ) {
            onSuccess(data)
        }
    }

    class Error<T>(private val data: T?) : FetchResultUiState<T> {
        @Composable
        override fun Show(
            onSuccess: @Composable (T) -> Unit,
            onError: @Composable (T?) -> Unit,
            onLoading: @Composable (T?) -> Unit
        ) {
            onError(data)
        }
    }

    class Loading<T>(private val data: T?) : FetchResultUiState<T> {
        @Composable
        override fun Show(
            onSuccess: @Composable (T) -> Unit,
            onError: @Composable (T?) -> Unit,
            onLoading: @Composable (T?) -> Unit
        ) {
            onLoading(data)
        }
    }

    class Initial<T> : FetchResultUiState<T> {
        @Composable
        override fun Show(
            onSuccess: @Composable (T) -> Unit,
            onError: @Composable (T?) -> Unit,
            onLoading: @Composable (T?) -> Unit
        ) {
        }
    }
}