package io.github.jakubherr.todo.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.destinations.TaskAddBottomSheetScreenDestination
import io.github.jakubherr.todo.tasks.model.Task
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun TaskListScreen(
    navigator: DestinationsNavigator,
    vm: TaskViewModel = koinViewModel(),
) {
    val taskList by vm.taskList.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(TaskAddBottomSheetScreenDestination) }) {
                Icon(imageVector = Icons.Default.Add, "Add task")
            }
        },
    ) {
        TaskList(
            Modifier.padding(it),
            "Category",
            taskList
        ) { task ->
            vm.checkTask(task)
        }
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    listName: String,
    tasks: List<Task>,
    onTaskChecked: (Task) -> Unit,
) {
    Card(modifier.wrapContentSize()) {
        LazyColumn(Modifier.padding(5.dp)) {
            item { TaskListHeader(listName) }
            items(tasks) { task ->
                TaskItem(task, onChecked = { onTaskChecked(task) })
            }
        }
    }
}

@Composable
fun TaskListHeader(listName: String) {
    Row(
        Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(listName, fontWeight = FontWeight.ExtraBold)

        IconButton(onClick = { /*TODO*/ }, Modifier.size(28.dp)) {
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
            }
            Checkbox(checked = task.completed, onCheckedChange = { onChecked(it) })
        }
    }
}
