package io.github.jakubherr.todo.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.ui.composables.TodoDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScaffold(
    navigator: DestinationsNavigator,
    title: String = "Title",
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, style = MaterialTheme.typography.headlineSmall) },
                actions = { TodoDropdownMenu() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, "Add task")
            }
        },
        bottomBar = { TodoNavigationBar(navigator) }
    ) { padding ->
        content(padding)
    }
}