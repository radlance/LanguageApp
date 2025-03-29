package com.radlance.languageapp.presentation.animal

import androidx.lifecycle.viewModelScope
import com.radlance.languageapp.domain.animal.Animal
import com.radlance.languageapp.domain.animal.AnimalRepository
import com.radlance.languageapp.presentation.common.BaseViewModel
import com.radlance.languageapp.presentation.common.FetchResultMapper
import com.radlance.languageapp.presentation.common.FetchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Дата создания: 29.03.2025
 * Автор: Манякин Дмитрий
 */

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : BaseViewModel() {

    private val _loadAnimalResultUiState =
        MutableStateFlow<FetchResultUiState<List<Animal>>>(FetchResultUiState.Initial())
    val loadAnimalResultUiState: StateFlow<FetchResultUiState<List<Animal>>> =
        _loadAnimalResultUiState.onStart {
            loadAnimals()
        }.stateInViewModel(initialValue = FetchResultUiState.Initial())

    private val _selectedAnimal = MutableStateFlow<Animal?>(null)
    val selectedAnimal: StateFlow<Animal?> = _selectedAnimal

    fun selectRandomAnimal(animals: List<Animal>) {
        _selectedAnimal.value = animals.random()
    }

    fun resetRandomAnswer() {
        _selectedAnimal.value = null
    }

    fun loadAnimals() {
        _loadAnimalResultUiState.value = FetchResultUiState.Loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            val result = animalRepository.animals()

            withContext(Dispatchers.Main) {
                _loadAnimalResultUiState.value = result.map(FetchResultMapper())
            }
        }
    }
}