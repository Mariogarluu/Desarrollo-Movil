package com.example.todoapp.data.model

// La clase TODO: representa una tarea
// data class = Kotlin genera toString(), equals(), copy(), etc automáticamente
data class Todo(
    val id: Long = System.currentTimeMillis(),  // ID único (usa el tiempo actual)
    val title: String,                           // Título de la tarea
    val description: String,                     // Descripción
    val completed: Boolean = false               // ¿Está completada?
)
