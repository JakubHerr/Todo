package io.github.jakubherr.todo.data.source.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.data.model.Project
import io.github.jakubherr.todo.data.ProjectRepository
import io.github.jakubherr.todo.data.model.Project.Companion.toProject
import io.github.jakubherr.todo.data.model.Task
import io.github.jakubherr.todo.data.model.Task.Companion.toTask
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FireProject: ProjectRepository {
    // TODO some error handling would not hurt
    private val projectCollection = Firebase.firestore.collection("projects")

    override suspend fun createProject(project: Project) {
        projectCollection.document(project.name).set(project)
    }

    override suspend fun getProject(name: String) {
        projectCollection.document(name).get()
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

    override fun subscribeToAllProjects(): Flow<List<Project>> = callbackFlow {
        val listener = projectCollection.addSnapshotListener { snapshot, firebaseError ->
            firebaseError?.let {
                cancel(message = "Error fetching projects", firebaseError)
                return@addSnapshotListener
            }

            snapshot?.let { update ->
                trySend(update.documents.mapNotNull { it.toProject() })
            }
        }

        awaitClose { listener.remove() }
    }

    override suspend fun getProjectTasks(projectName: String): List<Task> {
        val tasks = projectCollection.document(projectName).collection("tasks").get().await()
        return tasks.documents.mapNotNull { it.toTask() }
    }

    override fun subscribeToProjectTasks(projectName: String): Flow<List<Task>> = callbackFlow {
        val listener = projectCollection.document(projectName).collection("tasks").addSnapshotListener { snap, err ->
            err?.let {
                cancel(message = "Error fetching projects", it)
                return@addSnapshotListener
            }

            snap?.let { update ->
                trySend(update.documents.mapNotNull { it.toTask() })
            }
        }

        awaitClose { listener.remove() }
    }
}
