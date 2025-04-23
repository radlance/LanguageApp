package com.radlance.languageapp.presentation.game

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.game.Game
import com.radlance.languageapp.domain.game.GameRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val mapper: ConnectionStatusMapper
) : BaseViewModel() {
    private val _gameState = MutableStateFlow<Game?>(null)
    val gameState: StateFlow<Game?>
        get() = _gameState.asStateFlow()

    private val _createGameUiState =
        MutableStateFlow<FetchResultUiState<Game>>(FetchResultUiState.Initial())
    val createGameUiState: StateFlow<FetchResultUiState<Game>>
        get() = _createGameUiState.asStateFlow()

    private val _connectGameUiState =
        MutableStateFlow<FetchResultUiState<Game>>(FetchResultUiState.Initial())
    val connectGameUiState: StateFlow<FetchResultUiState<Game>>
        get() = _connectGameUiState.asStateFlow()

    val connectionStatusUiState = gameRepository.collectConnectionEvents().map {
        it.map(mapper)
    }.stateInViewModel(ConnectionStatusUiState.Disconnected)


    fun createGame() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameRepository.start()

            withContext(Dispatchers.Main) {
                _createGameUiState.value = result.map(FetchResultMapper())
            }
        }
    }

    fun connectToGame() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameRepository.gameConnect()

            withContext(Dispatchers.Main) {
                _connectGameUiState.value = result.map(FetchResultMapper())
            }
        }
    }

    fun collectGame(gameId: String) {
        gameRepository.collectGame(gameId).onEach { game ->
            _gameState.value = game
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            delay(timeMillis = 100)
            gameRepository.fetchGameContent(gameId)
        }
    }

    fun cancelGame(gameId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.cancelGame(gameId)
        }
    }

    fun answer(gameId: String, selectedAnswerIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.answer(gameId, selectedAnswerIndex)
        }
    }

    fun resetStates() {
        _createGameUiState.value = FetchResultUiState.Initial()
        _connectGameUiState.value = FetchResultUiState.Initial()
    }

    fun nextQuestion(gameId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.nextQuestion(gameId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameRepository.disconnectClient()
    }
}