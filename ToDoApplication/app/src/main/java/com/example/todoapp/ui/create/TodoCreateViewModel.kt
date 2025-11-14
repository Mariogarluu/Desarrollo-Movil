package com.example.todoapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoCreateViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    // ============ ESTADO DEL FORMULARIO ============

    // Título que escribe el usuario
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    // Descripción que escribe el usuario
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    // Estado de la UI (qué mostrar: formulario, éxito, etc)
    private val _uiState = MutableStateFlow<CreateUiState>(CreateUiState.Editing)
    val uiState: StateFlow<CreateUiState> = _uiState.asStateFlow()

    // ============ ACCIONES ============

    // Actualizar el título
    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    // Actualizar la descripción
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    // Crear el TODO
    fun createTodo() {
        // Validar que no esté vacío
        if (_title.value.isBlank() || _description.value.isBlank()) {
            _uiState.value = CreateUiState.Error("Llena todos los campos")
            return
        }

        viewModelScope.launch {
            try {
                // Agregar a la base de datos
                repository.addTodo(
                    title = _title.value,
                    description = _description.value
                )

                // Cambiar estado a "creado"
                _uiState.value = CreateUiState.Created
            } catch (e: Exception) {
                _uiState.value = CreateUiState.Error("Error al crear")
            }
        }
    }

    // Cancelar
    fun cancel() {
        _uiState.value = CreateUiState.Canceled
    }

    // Limpiar el estado (cuando navegas de vuelta)
    fun resetState() {
        _uiState.value = CreateUiState.Editing
        _title.value = ""
        _description.value = ""
    }
}

// ============ ESTADOS POSIBLES ============
sealed class CreateUiState {
    data object Editing : CreateUiState()      // Escribiendo
    data object Created : CreateUiState()      // Creado exitosamente
    data object Canceled : CreateUiState()     // Canceló
    data class Error(val message: String) : CreateUiState()  // Error
}
