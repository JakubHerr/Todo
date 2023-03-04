package io.github.jakubherr.todo.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.spec.Route
import io.github.jakubherr.todo.appCurrentDestinationAsState
import io.github.jakubherr.todo.destinations.Destination
import io.github.jakubherr.todo.startAppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScaffold(
    startRoute: Route,
    navController: NavController,
    topBar: @Composable (Destination) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val destination = navController.appCurrentDestinationAsState().value ?: startRoute.startAppDestination

    Scaffold(
        topBar = { topBar(destination) },
        bottomBar = { bottomBar(destination) },
        content = content,
    )
}
