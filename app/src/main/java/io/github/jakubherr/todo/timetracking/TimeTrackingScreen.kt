package io.github.jakubherr.todo.timetracking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.ui.composables.TodoScaffold

@Destination
@Composable
fun TimeTrackingScreen(
    navigator: DestinationsNavigator
) {
    TodoScaffold(navigator = navigator, title = "Timetracking") {
        Surface(Modifier.padding(it)) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Timetracking")
            }
        }
    }
}