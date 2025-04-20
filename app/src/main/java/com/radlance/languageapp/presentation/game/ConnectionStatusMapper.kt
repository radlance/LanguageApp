package com.radlance.languageapp.presentation.game

import com.radlance.languageapp.domain.game.ConnectionStatus
import javax.inject.Inject

class ConnectionStatusMapper @Inject constructor() : ConnectionStatus.Mapper<ConnectionStatusUiState> {

    override fun mapConnected(): ConnectionStatusUiState {
        return ConnectionStatusUiState.Connected
    }

    override fun mapDisconnected(): ConnectionStatusUiState {
        return ConnectionStatusUiState.Disconnected
    }

    override fun mapError(): ConnectionStatusUiState {
        return ConnectionStatusUiState.Error
    }
}