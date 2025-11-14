package com.example.todolist.viewmodel

import com.example.todolist.data.model.Task

sealed class ToDoUiState {
    object Idle : ToDoUiState()
    object Loading : ToDoUiState()
    data class Success(val tasks: List<Task>) : ToDoUiState()
    data class Error(val message: String) : ToDoUiState()
}
