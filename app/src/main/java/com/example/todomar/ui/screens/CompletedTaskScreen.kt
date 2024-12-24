package com.example.todomar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todomar.data.Task
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CompletedTasksScreen(
    tasks: List<Task>,
    onTaskUncomplete: (Task) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (tasks.isEmpty()) {
            Text(
                text = "No completed tasks",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            tasks.forEach { task ->
                // Format the completion date (if you store it as a Long)
                val formattedDate = formatCompletionDate(task.completionTimestamp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Tapping on the task moves it back to active
                        .clickable { onTaskUncomplete(task) }
                        .padding(vertical = 8.dp)
                ) {
                    // Task title
                    Text(
                        text = "✓ ${task.title}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    // Show completion date if available
                    if (formattedDate != null) {
                        Text(
                            text = "Completed on: $formattedDate",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

/**
 * A helper to format the completion date if stored as a Long (timestamp).
 * If you store it as a String, adjust accordingly.
 */
private fun formatCompletionDate(timestamp: Long?): String? {
    if (timestamp == null) return null
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
