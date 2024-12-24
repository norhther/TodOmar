package com.example.todomar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todomar.data.Task
@Composable
fun ActiveTasksScreen(
    tasks: List<Task>,
    onTaskChecked: (Task) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // <-- Make it scrollable
            .padding(16.dp)
    ) {
        if (tasks.isEmpty()) {
            Text(
                text = "No active tasks",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            tasks.forEach { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        // No swipe or clickable gestures needed
                        // If you still want to mark the task complete on tap,
                        // keep the clickable. Otherwise remove it.
                        .clickable { onTaskChecked(task) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = {
                            onTaskChecked(task)
                        }
                    )
                }
            }
        }
    }
}
