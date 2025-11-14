package com.example.todoapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel = "Hilt maneja la creación de esta clase"
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    // ============ ESTADO OBSERVABLE ============
    // StateFlow = Flujo que emite el estado actual
    // stateIn = Convierte un Flow a StateFlow
    // SharingStarted.Lazily = Empieza cuando hay observadores
    val todos: StateFlow<List<Todo>> = repository.getAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()  // Valor inicial: lista vacía
        )

    // ============ ACCIONES ============

    // Agregar un nuevo TODO
    fun addTodo(title: String, description: String) {
        // viewModelScope = Solo ejecuta mientras el ViewModel existe
        viewModelScope.launch {
            // Llamamos al repository (suspendible)
            repository.addTodo(title, description)
        }
    }

    // Marcar/desmarcar un TODO
    fun toggleTodo(todoId: Long) {
        viewModelScope.launch {
            repository.toggleTodo(todoId)
        }
    }
}
