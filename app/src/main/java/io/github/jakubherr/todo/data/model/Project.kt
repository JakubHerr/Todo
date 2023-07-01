package io.github.jakubherr.todo.data.model

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot

data class Project(
    val name: String = "", // project should have a unique name
    val tasks: List<String> = emptyList(),
) {
    companion object {
        fun DocumentSnapshot.toProject(): Project? {
            return try {
                Project(
                    id,
                    get("tasks") as? List<String> ?: emptyList()
                )
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
    }
}
