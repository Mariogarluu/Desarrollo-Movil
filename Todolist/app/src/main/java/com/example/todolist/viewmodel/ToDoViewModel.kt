package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Task
import com.example.todolist.data.repository.TaskInMemoryRepository
import com.example.todolist.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: TaskRepository,
    // Optional: si quieres usar getNextId() de la impl in-memory
    private val inMemoryRepo: TaskInMemoryRepository? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow<ToDoUiState>(ToDoUiState.Idle)
    val uiState: StateFlow<ToDoUiState> = _uiState

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = ToDoUiState.Loading
            delay(1000) // simulación de carga
            try {
                val tasks = repository.readAll()
                _uiState.value = ToDoUiState.Success(tasks)
            } catch (e: Exception) {
                _uiState.value = ToDoUiState.Error("Error cargando tareas")
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val newId = (inMemoryRepo?.getNextId())
                ?: (repository.readAll().maxOfOrNull { it.id }?.plus(1) ?: 1L)
            val task = Task(id = newId, title = title, description = description)
            repository.add(task)
            _uiState.value = ToDoUiState.Success(repository.readAll())
        }
    }

    fun getTaskById(id: Long): Task? = repository.readOne(id)
}













