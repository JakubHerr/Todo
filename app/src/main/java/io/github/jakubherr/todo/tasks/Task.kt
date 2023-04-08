package io.github.jakubherr.todo.tasks

import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.jakubherr.todo.ui.theme.TodoTheme

data class Task(val name: String, val checked: Boolean)

private val tasks = listOf(Task("Foo", false), Task("Bar", true))

@Preview(
    heightDp = 800,
    widthDp = 360,
    showBackground = true,
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun TaskListPreview() {
    TodoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(Modifier.padding(7.dp)) { TaskList("Category", tasks) }
        }
    }
}

@Composable
fun TaskList(
    listName: String,
    tasks: List<Task>,
) {
    Card(Modifier.wrapContentSize()) {
        LazyColumn(Modifier.padding(5.dp)) {
            item { TaskListHeader(listName) }
            items(tasks) {
                Task(text = it.name, checked = it.checked, onChecked = { /* TODO */ })
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
fun Task(
    text: String,
    checked: Boolean,
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
                Text(text, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(8.dp))
                Text("05.03.23 12:45", style = MaterialTheme.typography.bodySmall) // placeholder
            }
            Checkbox(checked = checked, onCheckedChange = { onChecked(it) })
        }
    }
}
