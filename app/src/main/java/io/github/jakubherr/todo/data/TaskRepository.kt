package io.github.jakubherr.todo.data

import io.github.jakubherr.todo.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)

    fun subscribeToAllTasks(): Flow<List<Task>>
    suspend fun checkTask(task: Task)
}