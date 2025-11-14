package com.example.todolist.di

import com.example.todolist.data.repository.TaskInMemoryRepository
import com.example.todolist.data.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskInMemoryRepository
    ): TaskRepository
}
