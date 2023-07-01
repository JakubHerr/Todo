package io.github.jakubherr.todo.data

import io.github.jakubherr.todo.data.model.Project
import io.github.jakubherr.todo.data.model.Task
import kotlinx.coroutines.flow.Flow

interface ProjectRepository { // maybe just replace references with identifiers
    suspend fun createProject(project: Project)
    suspend fun deleteProject(project: Project)
    suspend fun addTask(project: Project, task: Task)
    suspend fun removeTask(project: Project, task: Task)
    fun subscribeToAllProjects(): Flow<List<Project>>
}
