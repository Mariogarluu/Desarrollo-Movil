package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// @ActivityRetainedScoped = Mantiene la misma instancia mientras la actividad esté activa
@Singleton
class TodoRepositoryImpl @Inject constructor() : TodoRepository {

    // MutableStateFlow = contenedor mutable que notifica cambios
    // emptyList() = comienza vacío
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())

    // asStateFlow() = convierte a StateFlow (solo lectura, no mutable)
    // Los ViewModels observan esto
    private val todos = _todos.asStateFlow()

    // ============ OBTENER TODOS LOS TODOS ============
    override fun getAllTodos(): Flow<List<Todo>> {
        return todos  // Devolvemos el Flow observable
    }

    // ============ AGREGAR TODO ============
    override suspend fun addTodo(title: String, description: String) {
        // Obtenemos la lista actual
        val currentTodos = _todos.value.toMutableList()

        // Creamos un nuevo TODO
        val newTodo = Todo(
            title = title,
            description = description,
            completed = false
        )

        // Lo agregamos a la lista
        currentTodos.add(newTodo)

        // IMPORTANTE: Asignamos la lista actualizada
        // Esto notifica a TODOS los observers que hay cambios
        _todos.value = currentTodos
    }

    // ============ MARCAR COMPLETADO/SIN COMPLETAR ============
    override suspend fun toggleTodo(todoId: Long) {
        // Mapeamos la lista: si el ID coincide, invertimos "completed"
        val updatedTodos = _todos.value.map { todo ->
            if (todo.id == todoId) {
                // Encontramos el TODO: invertimos su estado
                todo.copy(completed = !todo.completed)
            } else {
                // No es este TODO: lo dejamos igual
                todo
            }
        }

        // Notificamos el cambio
        _todos.value = updatedTodos
    }

    // ============ OBTENER UN TODO POR ID ============
    override fun getTodoById(todoId: Long): Flow<Todo?> {
        // Mapeamos la lista de TODOs a un solo TODO
        // .map = transforma cada valor que emite el Flow
        return todos.map { todoList ->
            // Buscamos el TODO con ese ID
            todoList.find { it.id == todoId }
        }
    }
}
