package io.github.jakubherr.todo.data

interface ProjectRepository { // maybe just replace references with identifiers
    suspend fun createProject(project: Project)
    suspend fun deleteProject(project: Project)
    suspend fun addTask(project: Project, task: Task)
    suspend fun removeTask(project: Project, task: Task)
}