package io.github.jakubherr.todo.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun NoteListScreen(
    navigator: DestinationsNavigator
) {
    LazyColumn(Modifier.padding(horizontal = 16.dp)) {
        items(3) {
            Note()
        }
    }
}

@Composable
fun Note() {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Row {
            Column {
                Box(
                    Modifier
                        .height(32.dp)
                        .width(8.dp)
                        .background(Color.Green)
                )
            }
            Spacer(Modifier.width(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Title", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { /*TODO*/ }, Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.ChevronRight, "")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    navigator: DestinationsNavigator
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(value = "Title", onValueChange = {}, Modifier.fillMaxWidth(0.7f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Sell, contentDescription = "Tags")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Report, contentDescription = "Priority")
            }
        }

        TextField(
            value = "Content",
            onValueChange = {},
            singleLine = false,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
        )
    }
}
