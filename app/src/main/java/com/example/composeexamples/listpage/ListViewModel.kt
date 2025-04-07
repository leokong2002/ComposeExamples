package com.example.composeexamples.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeexamples.ext.sizeWithoutSpace
import com.example.composeexamples.repository.CatFactRepository
import com.example.composeexamples.repository.model.FactDataOfCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val catFactRepository: CatFactRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun handleEvent(event: UiEvent) {
        if (event is UiEvent.GetCatFact) {
            getCatFact()
        }
    }

    private fun getCatFact() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { catFactRepository.getData() }?.let { factData ->
                val newFact = FactDataOfCat(
                    message = factData.fact,
                    sizeOfMessage = factData.length,
                    sizeOfMessageWithoutSpace = factData.fact.sizeWithoutSpace(),
                )
                val updatedList = state.value.listOfFact.toMutableList()
                if (!updatedList.contains(newFact)) {
                    updatedList.add(newFact)
                } else {
                    getCatFact()
                    return@launch
                }
                _state.update {
                    it.copy(
                        numberOfFacts = updatedList.size,
                        listOfFact = updatedList
                    )
                }
            }
        }
    }

    sealed interface UiEvent {
        data object GetCatFact : UiEvent
    }

    data class UiState(
        val numberOfFacts: Int = 0,
        val listOfFact: List<FactDataOfCat> = emptyList()
    )
}
