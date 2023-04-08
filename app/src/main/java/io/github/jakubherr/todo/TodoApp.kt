package io.github.jakubherr.todo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import io.github.jakubherr.todo.destinations.Destination
import io.github.jakubherr.todo.destinations.LoginScreenDestination
import io.github.jakubherr.todo.ui.composables.TodoDropdownMenu
import io.github.jakubherr.todo.ui.composables.TodoNavigationBar
import io.github.jakubherr.todo.ui.composables.TodoScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp() {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    TodoScaffold(
        topBar = {
            TopAppBar(
                title = { Text("placeholder", style = MaterialTheme.typography.headlineSmall) },
                actions = { TodoDropdownMenu() }
            )
        },
        bottomBar = {
            TodoNavigationBar(navController)
        }
    ) { padding ->
        DestinationsNavHost(
            engine = engine,
            navController = navController,
            modifier = Modifier.padding(padding),
            navGraph = NavGraphs.root,
        )
    }
}

private val Destination.shouldShowAppBar get() = this !is LoginScreenDestination
