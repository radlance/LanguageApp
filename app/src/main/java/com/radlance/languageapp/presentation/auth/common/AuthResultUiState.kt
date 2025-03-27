package com.radlance.languageapp.presentation.auth.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Дата создания: 27.03.2025
 * Автор: Манякин Дмитрий
 */

interface AuthResultUiState {

    @Composable
    fun Show(
        onSuccessResult: () -> Unit,
        snackBarHostState: SnackbarHostState
    )

    object Initial : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
        }
    }

    class Loading(private val message: String) : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }

    object Success : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            LaunchedEffect(Unit) {
                onSuccessResult()
            }
        }
    }

    class Error(private val message: String) : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
            }
        }
    }
}