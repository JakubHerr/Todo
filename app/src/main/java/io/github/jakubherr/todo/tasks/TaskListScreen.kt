package io.github.jakubherr.todo.tasks

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.data.model.Priority
import io.github.jakubherr.todo.data.model.Project
import io.github.jakubherr.todo.destinations.TaskAddBottomSheetScreenDestination
import io.github.jakubherr.todo.data.model.Task
import io.github.jakubherr.todo.ui.theme.TodoTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    vm: TaskViewModel = koinViewModel(),
) {
    val taskList by vm.taskList.collectAsState(initial = emptyList())
    val projectList by vm.projectList.collectAsState(initial = emptyList())

    // fetch a list of projects and pass them to the drawer
    // hoist selectedItem here and make it inbox by default
    // onClick change selected item
    // selected item should influence title and shown tasks

    TodoDrawer(
        projectList = projectList
    ) {
        TaskListScreen(
            taskList,
            onAddTaskClicked = { navigator.navigate(TaskAddBottomSheetScreenDestination) },
            onTaskChecked = { task -> vm.checkTask(task)}
        )
    }
}

@Composable
fun TaskListScreen(
    taskList: List<Task>,
    onAddTaskClicked: () -> Unit,
    onTaskChecked: (Task) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onAddTaskClicked) {
                Icon(imageVector = Icons.Default.Add, "Add task")
            }
        },
    ) {
        TaskList(
            Modifier.padding(it),
            "Category",
            taskList,
            onTaskChecked
        )
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    listName: String,
    tasks: List<Task>,
    onTaskChecked: (Task) -> Unit,
) {
    var collapsed by remember { mutableStateOf(false) }

    Card(
        modifier
            .padding(10.dp)
            .animateContentSize()) {
        LazyColumn(Modifier.padding(5.dp)) {
            item { TaskListHeader(
                listName,
                onCollapsed = { collapsed = !collapsed }
            ) }
            items(tasks) { task ->
                if (!collapsed) TaskItem(task, onChecked = { onTaskChecked(task) })
            }
        }
    }
}

@Composable
fun TaskListHeader(
    listName: String,
    onCollapsed: () -> Unit,
) {


    Row(
        Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(listName, fontWeight = FontWeight.ExtraBold)

        IconButton(onClick = onCollapsed, Modifier.size(28.dp)) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, "")
        }
    }
    Divider(color = MaterialTheme.colorScheme.secondary)
}

@Composable
fun TaskItem(
    task: Task,
    onChecked: (Boolean) -> Unit,
) {
    Card(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(task.name, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(8.dp))
                Text(task.deadline ?: "", style = MaterialTheme.typography.bodySmall) // placeholder
                Spacer(Modifier.width(8.dp))
                Box(Modifier.background(Color(0,255,0,0x30))) {
                    // TODO show first X tags in UI
                    Text((task.tags.firstOrNull()?.prependIndent("#")) ?: "", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.ExtraBold)
                }
            }
            Checkbox(checked = task.completed, onCheckedChange = { onChecked(it) })
        }
    }
}

@Composable
fun TodoDrawer(
    projectList: List<Project>,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedProject = remember { mutableStateOf(Project("", emptyList())) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                projectList.forEach { item ->
                    NavigationDrawerItem(
                        // icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item.name == selectedProject.value.name,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedProject.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = content
    )
}

@Preview
@Composable
fun TaskListScreenPreview() {
    val taskList = listOf(
        Task("0", "foo", priority = Priority.HIGH, deadline = "2023-04-13 13:30"),
        Task("0", "bar", completed = true, priority = Priority.MEDIUM, tags = listOf("foo", "bar")),
        Task("0", "baz", priority = Priority.HIGH, tags = listOf("Test"))
    )

    TodoTheme(darkTheme = true) {
        TaskListScreen(
            taskList,
            {},
            {},
        )
    }
}
