package com.example.composeexamples.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeexamples.ext.sizeWithoutSpace
import com.example.composeexamples.repository.CatFactRepository
import com.example.composeexamples.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        getCatFactData()
    }

    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ReloadCatFact -> getCatFactData()
            is UiEvent.AddToFavourites -> addToFavourites()
        }
    }

    private fun getCatFactData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { catFactRepository.getData() }?.let { factData ->
                _state.update {
                    it.copy(
                        message = factData.fact,
                        sizeOfMessage = factData.length,
                        sizeOfMessageWithoutSpace = factData.fact.sizeWithoutSpace(),
                    )
                }
            }
        }
    }

    private fun addToFavourites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favouritesRepository.insertCatFact(
                    message = state.value.message,
                    sizeOfMessage = state.value.sizeOfMessage,
                    sizeOfMessageWithoutSpace = state.value.sizeOfMessageWithoutSpace
                )
            }
        }
    }

    sealed interface UiEvent {
        data object AddToFavourites : UiEvent
        data object ReloadCatFact : UiEvent
    }

    data class UiState(
        val title: String = "Cat Fact",
        val message: String = "",
        val sizeOfMessage: Int = 0,
        val sizeOfMessageWithoutSpace: Int = 0,
    )
}
