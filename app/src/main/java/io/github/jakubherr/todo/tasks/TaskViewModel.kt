package io.github.jakubherr.todo.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jakubherr.todo.data.ProjectRepository
import io.github.jakubherr.todo.data.TaskRepository
import io.github.jakubherr.todo.data.model.Priority
import io.github.jakubherr.todo.data.model.Project
import io.github.jakubherr.todo.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    val taskList: Flow<List<Task>> = taskRepository.subscribeToAllTasks()
    val projectList: Flow<List<Project>> = projectRepository.subscribeToAllProjects()
    // temporary, should listen to snapshots, but also be able to change the project being listened to
    var projectTaskList: SnapshotStateList<Task> = mutableStateListOf()

    init {
        viewModelScope.launch { getProjectTasks("Inbox") }
    }

    fun addTask(name: String, priority: Priority, deadline: LocalDateTime? = null) {
        viewModelScope.launch {
            taskRepository.addTask(
                Task(
                    name = name,
                    completed = false,
                    priority = priority,
                    deadline = deadline?.toInstant(TimeZone.UTC).toString() // TODO make this less dumb
                )
            )
        }
    }

    fun checkTask(task: Task) {
        viewModelScope.launch { taskRepository.checkTask(task) }
    }

    fun subscribeToProjectTasks(name: String) {
        projectRepository.subscribeToProjectTasks(name)
    }

    fun getProjectTasks(name: String) {
        viewModelScope.launch {
            projectTaskList.clear()
            projectTaskList.addAll(projectRepository.getProjectTasks(name))
        }
    }
}
