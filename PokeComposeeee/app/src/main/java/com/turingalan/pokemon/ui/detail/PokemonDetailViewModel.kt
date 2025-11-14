package com.turingalan.pokemon.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import com.turingalan.pokemon.ui.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor (
    private val savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<DetailUiSate> = MutableStateFlow(DetailUiSate())
    val uiState: StateFlow<DetailUiSate>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Destinations.Details>()
            repository.readOne(route.id).collect { pokemon ->
                pokemon?.let {
                    _uiState.value = it.toDetailUiState()
                }
            }
        }
    }

    fun deleteChampion() {
        viewModelScope.launch {
            try {
                val route = savedStateHandle.toRoute<Destinations.Details>()
                val success = repository.deleteOne(route.id)
                savedStateHandle["delete_success"] = success
                if (success) {
                    _uiState.value = DetailUiSate()
                }
            }
            catch (e: Exception) {
                savedStateHandle["delete_success"] = false
            }
        }
    }
}

fun Pokemon.toDetailUiState(): DetailUiSate {
    return DetailUiSate(
        this.name,
        this.artworkId
    )
}

data class DetailUiSate(
    val name: String = "",
    val artworkId: LocalizedString = LocalizedString.Plain("")
)