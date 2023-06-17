package io.github.jakubherr.todo.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)

    fun subscribeToAllTasks(): Flow<List<Task>>
    suspend fun checkTask(task: Task)
}