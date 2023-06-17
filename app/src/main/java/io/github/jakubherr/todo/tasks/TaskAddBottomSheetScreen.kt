package io.github.jakubherr.todo.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.github.jakubherr.todo.data.Priority
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun ColumnScope.TaskAddBottomSheetScreen(
    navigator: DestinationsNavigator,
    vm: TaskViewModel = koinViewModel(),
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(256.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Add new task", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.padding(vertical = 5.dp))

            var name by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
            )

            var priority by remember { mutableStateOf(Priority.LOW) }
            PriorityPicker(onPriorityPicked = {
                priority = it
            })

            var deadline by remember { mutableStateOf<LocalDateTime?>(null) }
            DateTimePicker {
                deadline = it
            }

            Button(onClick = {
                if (name.isNotBlank()) vm.addTask(name, priority, deadline)
                navigator.navigateUp()
            }) {
                Icon(Icons.Default.Add, "")
                Text("Add")
            }
        }
    }
}

@Composable
fun PriorityPicker(
    onPriorityPicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Button(onClick = { expanded = true }) {
        Icon(Icons.Default.PriorityHigh, "")
        Text("Priority")

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Priority.values().forEach {
                DropdownMenuItem(
                    text = { Text(it.label) },
                    leadingIcon = { Icon(it.icon, "") },
                    onClick = {
                        onPriorityPicked(it)
                        expanded = false
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    onDateAndTimePicked: (LocalDateTime) -> Unit = { }
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()
    val timeState = rememberTimePickerState()

    Button(onClick = { showDatePicker = true }) {
        Text("Add deadline")
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        showDatePicker = false
                        showTimePicker = true
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(dateState)
            }
        }
    }

    if (showTimePicker) {
        // TODO use TimePickerDialog once it exists
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Surface(Modifier.fillMaxWidth().wrapContentHeight()) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    TimePicker(timeState)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { /*TODO*/ }) {
                            Text("Cancel")
                        }
                        Button(onClick = {
                            // TODO this is dumb
                            val millis = dateState.selectedDateMillis!! + (timeState.hour * 3600 + timeState.minute * 60) * 1000
                            onDateAndTimePicked(Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC))
                        }) {
                            Text("Confirm")
                        }
                    }
                }

            }
        }
    }
}
