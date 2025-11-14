package com.example.todolist.data.repository

import com.example.todolist.data.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskInMemoryRepository @Inject constructor() : TaskRepository {

    private val tasks: MutableList<Task> = mutableListOf(
        Task(1, "Comprar leche", "Ir al supermercado y comprar leche"),
        Task(2, "Estudiar", "Repasar temas para el examen"),
        Task(3, "Llamar a Ana", "Confirmar plan para el fin de semana")
    )

    private var nextId = (tasks.maxOfOrNull { it.id } ?: 0L) + 1L

    override fun readAll(): List<Task> = tasks.toList()

    override fun readOne(id: Long): Task? = tasks.firstOrNull { it.id == id }

    override fun add(task: Task) {
        tasks.add(task)
        nextId = (tasks.maxOfOrNull { it.id } ?: 0L) + 1L
    }

    fun getNextId(): Long = nextId
}
