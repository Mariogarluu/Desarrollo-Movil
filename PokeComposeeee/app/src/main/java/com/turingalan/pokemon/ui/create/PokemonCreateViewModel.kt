package com.turingalan.pokemon.ui.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.data.model.LocalizedString
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCreateViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()
    fun updateName(newName: String) {
        _name.value = newName
    }
    private val _spriteId = MutableStateFlow(LocalizedString.Plain(""))
    val spriteId: StateFlow<LocalizedString.Plain> = _spriteId.asStateFlow()
    fun updateSpriteId(newSpriteId: LocalizedString.Plain) {
        _spriteId.value = newSpriteId
    }
    private val _artworkId = MutableStateFlow(LocalizedString.Plain(""))
    val artworkId: StateFlow<LocalizedString.Plain> = _artworkId.asStateFlow()
    fun updateArtworkId(newArtworkId: LocalizedString.Plain) {
        _artworkId.value = newArtworkId
    }

    private val _uiState = MutableStateFlow<CreateUiState>(CreateUiState.New)
    val uiState: StateFlow<CreateUiState> = _uiState.asStateFlow()

    fun createPokemon() {
        if (_name.value.isBlank() || _spriteId.value.text.isBlank() || _artworkId.value.text.isBlank()) {
            _uiState.value = CreateUiState.Error("All fields are mandatory")
            return
        }
        viewModelScope.launch {
            try {
                repository.createOne(
                    name = _name.value,
                    spriteId = _spriteId.value,
                    artworkId = _artworkId.value
                )
                _uiState.value = CreateUiState.Created
            }
            catch (e: Exception) {
                _uiState.value = CreateUiState.Error("Error creating")
            }
        }
    }

    fun cancel() {
        _uiState.value = CreateUiState.Canceled
    }

    fun resetState() {
        _uiState.value = CreateUiState.New
        _name.value = ""
        _spriteId.value = LocalizedString.Plain("")
        _artworkId.value = LocalizedString.Plain("")
    }
}

sealed class CreateUiState() {
    data object New: CreateUiState()
    data object Created: CreateUiState()
    data object Canceled: CreateUiState()
    data class Error(val message: String): CreateUiState()
}