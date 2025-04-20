package com.radlance.languageapp.presentation.game

interface ConnectionStatusUiState {

    fun apply(
        gameId: String,
        viewModel: GameViewModel
    )

    object Connected : ConnectionStatusUiState {
        override fun apply(gameId: String, viewModel: GameViewModel) {
            viewModel.collectGame(gameId)
        }
    }

    object Disconnected : ConnectionStatusUiState {
        override fun apply(gameId: String, viewModel: GameViewModel) = Unit
    }

    object Error : ConnectionStatusUiState {
        override fun apply(gameId: String, viewModel: GameViewModel) = Unit
    }
}