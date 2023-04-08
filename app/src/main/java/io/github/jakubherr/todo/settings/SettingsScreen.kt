package io.github.jakubherr.todo.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.ui.composables.TodoScaffold

// TODO add settings
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    TodoScaffold(navigator = navigator, title = "Settings") {
        Surface(Modifier.padding(it)) {
            Column(Modifier.padding(5.dp)) {
                Text("Settings")
            }
        }
    }
}