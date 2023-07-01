package io.github.jakubherr.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import io.github.jakubherr.todo.data.model.Project
import io.github.jakubherr.todo.data.ProjectRepository
import io.github.jakubherr.todo.data.model.Task
import io.github.jakubherr.todo.ui.theme.TodoTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        projectTest()

        setContent {
            TodoTheme {
                TopLevelDestination()
            }
        }
    }

    // TODO test properly
    fun projectTest() {
        // project test
        val fireProject: ProjectRepository by inject()
        lifecycleScope.launch {
            val project = Project("Hello World", emptyList())
            val task = Task(id = "123", name = "foo")
            val task2 = Task(id = "124", name = "foo")
            val task3 = Task(id = "125", name = "foo")
            val task4 = Task(id = "126", name = "foo")
            fireProject.createProject(project)
            fireProject.addTask(project, task)
            fireProject.addTask(project, task2)
            fireProject.addTask(project, task3)
            fireProject.removeTask(project, task4)
        }
    }
}
