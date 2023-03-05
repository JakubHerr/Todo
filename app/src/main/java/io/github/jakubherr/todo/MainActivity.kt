package io.github.jakubherr.todo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.jakubherr.todo.ui.theme.TodoTheme
import io.github.jakubherr.todo.ui.theme.shared.TaskListPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenPreview()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    heightDp = 800,
    widthDp = 360,
    showBackground = true,
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun MainScreenPreview() {
    var expanded by remember { mutableStateOf(false) }

    TodoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Overview", style = MaterialTheme.typography.headlineSmall) },
                    actions = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, "")
                            MainDropdownMenu(expanded = expanded) { expanded = false }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Add, "Add task")
                }
            },
            bottomBar = { TodoNavigationBar() }
        ) { padding ->
            Surface(Modifier.padding(padding)) {
                TaskListPreview()
            }
        }
    }
}

@Composable
fun MainDropdownMenu(expanded: Boolean, onDismiss: () -> Unit) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        DropdownMenuItem(text = { Text(text = "Sort") }, leadingIcon = { Icon(Icons.Default.Sort, "") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "Filter") }, leadingIcon = { Icon(Icons.Default.FilterList, "") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "Show completed") }, leadingIcon = { Icon(Icons.Default.ChecklistRtl, "") }, onClick = { /*TODO*/ })
    }
}

@Composable
fun TodoNavigationBar() {
    NavigationBar(
        Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        windowInsets = WindowInsets(left = 5.dp, right = 5.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.List, "") })
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Today, "") })
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Schedule, "") })
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Settings, "") })
    }
}
