package io.github.jakubherr.todo.data.source.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.data.Project
import io.github.jakubherr.todo.data.ProjectRepository
import io.github.jakubherr.todo.data.Task
import kotlinx.coroutines.tasks.await

class FireProject: ProjectRepository {
    // TODO some error handling would not hurt
    private val projectCollection = Firebase.firestore.collection("projects")

    override suspend fun createProject(project: Project) {
        projectCollection.document(project.name).set(project)
    }

    override suspend fun deleteProject(project: Project) {
        projectCollection.document(project.name).delete().await()
    }

    override suspend fun addTask(project: Project, task: Task) {
        projectCollection.document(project.name).update("tasks", FieldValue.arrayUnion(task.id)).await()
    }

    override suspend fun removeTask(project: Project, task: Task) {
        projectCollection.document(project.name).update("tasks", FieldValue.arrayRemove(task.id)).await()
    }
}