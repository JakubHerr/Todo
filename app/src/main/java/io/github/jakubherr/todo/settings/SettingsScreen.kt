package io.github.jakubherr.todo.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import io.github.jakubherr.todo.NavGraphs
import io.github.jakubherr.todo.destinations.LoginScreenDestination
import io.github.jakubherr.todo.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

// TODO add settings
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    vm: LoginViewModel = koinViewModel()
) {
    Column(Modifier.padding(5.dp)) {
        Text("Settings")
        Button(onClick = {
            Log.d("DBG","Logging out...")
            vm.logout()
            navigator.navigate(LoginScreenDestination) {
                popUpTo(NavGraphs.root)
            }
        }) {
            Text("Log out")
        }
    }
}
