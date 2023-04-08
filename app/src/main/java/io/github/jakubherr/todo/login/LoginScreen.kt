package io.github.jakubherr.todo.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jakubherr.todo.R
import io.github.jakubherr.todo.destinations.TaskListScreenDestination


@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val auth = remember { Firebase.auth }

    fun register(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("DBG", "createUserWithEmail:success")
                navigator.navigate(TaskListScreenDestination)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("DBG", "createUserWithEmail:failure", task.exception)
            }
        }

    fun login(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("DBG", "signInWithEmail:success")
                navigator.navigate(TaskListScreenDestination)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("DBG", "signInWithEmail:failure", task.exception)
            }
        }

    Surface(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(R.string.login))
            Spacer(Modifier.padding(vertical = 5.dp))

            EmailField(
                email = email,
                onChange = { email = it }
            )

            Spacer(Modifier.padding(vertical = 5.dp))

            PasswordField(
                password,
                showPassword,
                onChange = { password = it },
                onVisibilityChange = { showPassword = !showPassword }
            )

            Spacer(Modifier.padding(vertical = 5.dp))

            // TODO make a separate screen for login and registration
            Button(
                onClick = { login(email, password) },
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Log in")
            }
            Button(
                onClick = { register(email, password) },
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Register")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EmailField(
    email: String,
    onChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = email,
        onValueChange = onChange,
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Mail, "", modifier = Modifier.size(24.dp)) },
        label = { Text(stringResource(R.string.e_mail)) },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PasswordField(
    password: String,
    showPassword: Boolean,
    onChange: (String) -> Unit,
    onVisibilityChange: () -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onChange,
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Key, "", modifier = Modifier.size(24.dp)) },
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                Icon(
                    if (!showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    "",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        label = { Text(stringResource(R.string.password)) },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
}
