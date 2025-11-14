package com.example.todoapp.di

import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.data.repository.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// @Module = "Este archivo contiene reglas de inyección"
@Module
// @InstallIn(SingletonComponent::class) = "Usar en toda la app (singleton)"
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // @Binds = "Cuando alguien pida TodoRepository, dale TodoRepositoryImpl"
    // abstract = Hilt genera el código automáticamente
    @Singleton
    @Binds
    abstract fun bindTodoRepository(
        impl: TodoRepositoryImpl
    ): TodoRepository
}
