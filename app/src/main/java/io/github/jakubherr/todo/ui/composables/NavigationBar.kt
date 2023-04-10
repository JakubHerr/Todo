package io.github.jakubherr.todo.ui.composables

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import io.github.jakubherr.todo.NavGraphs
import io.github.jakubherr.todo.destinations.DirectionDestination
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
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        windowInsets = WindowInsets(left = 5.dp, right = 5.dp)
    ) {
        NavigationItem.values().forEach { destination ->
            val isSelected = navController.isRouteOnBackStack(destination.direction)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) navController.navigate(destination.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = "") },
                label = { Text(destination.label) },
            )
        }
    }
}

enum class NavigationItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    val label: String,
) {
    Tasks(TaskListScreenDestination, Icons.Default.List, "Tasks"),
    Notes(NoteListScreenDestination, Icons.Outlined.Book, "Notes"),
    Timetracking(TimeTrackingScreenDestination, Icons.Default.Schedule, "Timetrack"),
    Settings(SettingsScreenDestination, Icons.Default.Settings, "Settings"),
}
