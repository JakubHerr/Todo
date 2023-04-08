package io.github.jakubherr.todo.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TodoDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) { Icon(Icons.Default.MoreVert, "") }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text(text = "Sort") },
            leadingIcon = { Icon(Icons.Default.Sort, "") },
            onClick = { /*TODO*/ })
        DropdownMenuItem(
            text = { Text(text = "Filter") },
            leadingIcon = { Icon(Icons.Default.FilterList, "") },
            onClick = { /*TODO*/ })
        DropdownMenuItem(
            text = { Text(text = "Show completed") },
            leadingIcon = { Icon(Icons.Default.ChecklistRtl, "") },
            onClick = { /*TODO*/ })
    }
}
