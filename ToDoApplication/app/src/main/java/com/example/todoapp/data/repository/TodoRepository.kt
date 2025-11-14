package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

// Interfaz = Define QUÉ hacer (no CÓMO hacerlo)
// Esto permite cambiar la implementación sin afectar el resto del código
interface TodoRepository {

    // Obtener todos los TODOs como Flow (observable)
    // Flow = Se actualiza automáticamente cuando hay cambios
    fun getAllTodos(): Flow<List<Todo>>

    // Agregar un nuevo TODO
    suspend fun addTodo(title: String, description: String)

    // Actualizar el estado "completado" de un TODO
    suspend fun toggleTodo(todoId: Long)

    // Obtener un TODO específico por su ID
    fun getTodoById(todoId: Long): Flow<Todo?>
}
