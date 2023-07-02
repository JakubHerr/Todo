package io.github.jakubherr.todo.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot

data class Task(
    val id: String = "", // empty by default, unique id gets added in TaskRepository
    val name: String = "",
    val projectName: String = "Inbox",
    val completed: Boolean = false,
    val priority: Priority = Priority.LOW,
    val deadline: String? = null, // ISO 8601 TODO handle timezones
    val tags: List<String> = emptyList(),
) {
    companion object {
        fun DocumentSnapshot.toTask(): Task? {
            return try {
                Task(
                    id,
                    getString("name")!!,
                    getString("projectName") ?: "Inbox",
                    getBoolean("completed")!!,
                    Priority.valueOf(getString("priority")!!),
                    getString("deadline"),
                    get("tags") as? List<String> ?: emptyList(),
                )
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
    }
}

enum class Priority( // TODO add some better icons, use string resource
    val label: String,
    val icon: ImageVector,
) {
    LOW("Low", Icons.Default.ChevronRight),
    MEDIUM("Medium", Icons.Default.KeyboardDoubleArrowRight),
    HIGH("High", Icons.Default.PriorityHigh),
}
