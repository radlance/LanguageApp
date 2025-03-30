package com.radlance.languageapp.presentation.practice

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.game.GameData
import com.radlance.languageapp.domain.game.GameRepository
import com.radlance.languageapp.domain.game.Question
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Дата создания: 30.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : BaseViewModel() {
    private val _loadGameResultUiState =
        MutableStateFlow<FetchResultUiState<GameData>>(FetchResultUiState.Initial())
    val loadGameResultUiStat: StateFlow<FetchResultUiState<GameData>> =
        _loadGameResultUiState.onStart {
            loadGame()
        }.stateInViewModel(initialValue = FetchResultUiState.Initial())

    private val _selectedQuestion = MutableStateFlow<Question?>(null)
    val selectedQuestion: StateFlow<Question?> = _selectedQuestion.asStateFlow()

    fun loadGame() {
        _loadGameResultUiState.value = FetchResultUiState.Loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            val result = gameRepository.game()

            withContext(Dispatchers.Main) {
                _loadGameResultUiState.value = result.map(FetchResultMapper())
            }
        }
    }

    fun selectRandomWord(questions: List<Question>) {
        _selectedQuestion.value = questions.random()
    }
}