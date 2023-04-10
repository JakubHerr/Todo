package io.github.jakubherr.todo.tasks.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.tasks.model.Task
import io.github.jakubherr.todo.tasks.model.Task.Companion.toTask
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface TaskRepository {
    suspend fun addTask(task: Task)

    fun subscribeToAllTasks(): Flow<List<Task>>
    suspend fun checkTask(task: Task)

    companion object {
        operator fun invoke(): TaskRepository = object : TaskRepository {
            private val taskCollection = Firebase.firestore.collection("tasks")

            override suspend fun addTask(task: Task) {
                taskCollection.add(task.copy(id = taskCollection.id)).await()
                // TODO add some error handling
            }

            override suspend fun checkTask(task: Task) {
                taskCollection.document(task.id).set(task.copy(completed = !task.completed))
            }

            override fun subscribeToAllTasks(): Flow<List<Task>> = callbackFlow {
                val listener = taskCollection.addSnapshotListener { snapshot, firebaseError ->
                    firebaseError?.let {
                        cancel(message = "Error fetching posts", cause = firebaseError)
                        return@addSnapshotListener
                    }

                    snapshot?.let { update ->
                        trySend(update.documents.mapNotNull { it.toTask() })
                    }
                }

                awaitClose { listener.remove() }
            }
        }
    }
}