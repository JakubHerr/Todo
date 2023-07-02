package io.github.jakubherr.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.data.model.Task
import io.github.jakubherr.todo.data.model.Task.Companion.toTask
import io.github.jakubherr.todo.ui.theme.TodoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // projectTest()

        setContent {
            TodoTheme {
                TopLevelDestination()
            }
        }
    }

    // TODO test properly
    fun projectTest() {
        // project test
        lifecycleScope.launch {
            val ref = Firebase.firestore
                .collection("projects")
                .document("Hello World")
                .collection("tasks")

            val task = Task(id = "123", name = "foo").copy(id = ref.id)
            val task2 = Task(id = "124", name = "foo").copy(id = ref.id)
            val task3 = Task(id = "125", name = "foo").copy(id = ref.id)

            ref.add(task)
            ref.add(task2)
            ref.add(task3)

            ref.addSnapshotListener { snap, err ->
                snap?.let {
                    val test = it.documents.mapNotNull { it.toTask() }
                    Log.d("DBG", test.toString())
                }
            }
        }
    }
}
