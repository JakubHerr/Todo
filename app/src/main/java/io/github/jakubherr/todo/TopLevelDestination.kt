package io.github.jakubherr.todo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import io.github.jakubherr.todo.destinations.Destination
import io.github.jakubherr.todo.destinations.LoginScreenDestination
import io.github.jakubherr.todo.login.UserViewModel
import io.github.jakubherr.todo.ui.composables.TodoDropdownMenu
import io.github.jakubherr.todo.ui.composables.TodoNavigationBar
import io.github.jakubherr.todo.ui.composables.TodoScaffold
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun TopLevelDestination(
    vm: UserViewModel = koinViewModel()
) {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    val startRoute =
        if (vm.currentUser() == null) LoginScreenDestination else NavGraphs.root.startRoute

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        TodoScaffold(
            startRoute = startRoute,
            navController = navController,
            topBar = { destinaiton ->
                if (destinaiton.shouldShowAppBar) TopAppBar(
                    title = { Text("placeholder", style = MaterialTheme.typography.headlineSmall) },
                    actions = { TodoDropdownMenu() }
                )
            },
            bottomBar = { destination ->
                if (destination.shouldShowAppBar) TodoNavigationBar(navController)
            }
        ) { padding ->
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                modifier = Modifier.padding(padding),
                navGraph = NavGraphs.root,
                startRoute = startRoute,
            )
        }
    }
}

private val Destination.shouldShowAppBar get() = this !is LoginScreenDestination
