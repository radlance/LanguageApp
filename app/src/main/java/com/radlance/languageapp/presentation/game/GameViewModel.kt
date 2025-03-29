package com.radlance.languageapp.presentation.game

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.game.Game
import com.radlance.languageapp.domain.game.GameRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Дата создания: 28.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : BaseViewModel() {

    private val _createGameResultUiState =
        MutableStateFlow<FetchResultUiState<Game>>(FetchResultUiState.Initial())
    val createGameResultUiState: StateFlow<FetchResultUiState<Game>>
        get() = _createGameResultUiState.asStateFlow()

    fun createGame() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameRepository.start()

            withContext(Dispatchers.Main) {
                _createGameResultUiState.value = result.map(FetchResultMapper())
            }
        }
    }
}