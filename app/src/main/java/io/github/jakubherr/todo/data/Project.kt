package io.github.jakubherr.todo.data

data class Project(
    val name: String = "", // project should have a unique name
    val tasks: List<String>,
)
