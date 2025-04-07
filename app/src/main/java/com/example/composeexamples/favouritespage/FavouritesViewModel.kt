package com.example.composeexamples.favouritespage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeexamples.database.model.FavouriteCatFact
import com.example.composeexamples.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = combine(_state, favouritesRepository.favouriteCatFacts) { state, favouriteCatFacts ->
        state.copy(
            numberOfFavourites = favouriteCatFacts.size,
            catFacts = favouriteCatFacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _state.value)

    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.DeleteAllFacts -> deleteAllFacts()
            is UiEvent.DeleteFact -> deleteFact(event.id)
        }
    }

    private fun deleteAllFacts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritesRepository.deleteAllFacts()
            }
        }
    }

    private fun deleteFact(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritesRepository.deleteFact(id)
            }
        }
    }

    sealed interface UiEvent {
        data object DeleteAllFacts : UiEvent
        data class DeleteFact(val id: Long) : UiEvent
    }

    data class UiState(
        val numberOfFavourites: Int = 0,
        val catFacts: List<FavouriteCatFact> = emptyList()
    )

    private companion object {
        private const val TITLE_FORMAT = "{NUMBER} Favourite Cat Facts"
    }
}
