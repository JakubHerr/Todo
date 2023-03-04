package io.github.jakubherr.todo.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jakubherr.todo.tasks.data.TaskRepository
import io.github.jakubherr.todo.tasks.model.Priority
import io.github.jakubherr.todo.tasks.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    var taskList: Flow<List<Task>> = repository.subscribeToAllTasks()

    fun addTask(name: String, priority: Priority, deadline: LocalDateTime? = null) {
        viewModelScope.launch {
            repository.addTask(
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
        viewModelScope.launch { repository.checkTask(task) }
    }
}