package com.example.todoapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle  // Para obtener parámetros de navegación
) : ViewModel() {

    // Obtener el ID del TODO desde los parámetros de navegación
    private val todoId: Long = checkNotNull(savedStateHandle["todoId"])

    // ============ ESTADO OBSERVABLE ============
    // Obtener el TODO específico
    // filterNotNull() = Si es null, no emite nada
    val todo: Flow<Todo> = repository.getTodoById(todoId).filterNotNull()
}
