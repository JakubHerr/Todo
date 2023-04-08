package io.github.jakubherr.todo.tasks

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.ui.composables.TodoScaffold

@RootNavGraph(start = true)
@Destination
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator
) {
    TodoScaffold(navigator, title = "Tasks") { padding ->
        Surface(Modifier.padding(padding)) {
            TaskListPreview()
        }
    }
}