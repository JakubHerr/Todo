package io.github.jakubherr.todo.ui.composables

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import io.github.jakubherr.todo.destinations.NoteListScreenDestination
import io.github.jakubherr.todo.destinations.SettingsScreenDestination
import io.github.jakubherr.todo.destinations.TaskListScreenDestination
import io.github.jakubherr.todo.destinations.TimeTrackingScreenDestination

@Composable
fun TodoNavigationBar(
    navController: NavController
) {
    NavigationBar(
        Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        windowInsets = WindowInsets(left = 5.dp, right = 5.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(TaskListScreenDestination) },
            icon = { Icon(Icons.Default.List, "") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NoteListScreenDestination) },
            icon = { Icon(Icons.Outlined.Book, "") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(TimeTrackingScreenDestination) },
            icon = { Icon(Icons.Default.Schedule, "") })
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(SettingsScreenDestination) },
            icon = { Icon(Icons.Default.Settings, "") })
    }
}
