package com.example.todomar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import todomar.ui.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp(taskViewModel: TaskViewModel) {
    // Control which tab is selected
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Active", "Completed")

    // State flows from ViewModel
    val activeTasks = taskViewModel.uiState.collectAsState().value.activeTasks
    val completedTasks = taskViewModel.completedTasksFlow.collectAsState().value

    // For the overflow menu
    var showMenu by remember { mutableStateOf(false) }

    // NEW: State for dialog visibility
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TodOmar") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete All Completed") },
                            onClick = {
                                taskViewModel.deleteAllCompleted()
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.DeleteSweep,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Instead of auto-adding a task, show the dialog
                showAddDialog = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> ActiveTasksScreen(
                    tasks = activeTasks,
                    onTaskChecked = { taskViewModel.completeTask(it) }
                )
                1 -> CompletedTasksScreen(
                    tasks = completedTasks,
                    onTaskUncomplete = { taskViewModel.uncompleteTask(it) } // We'll define this in the ViewModel
                )
            }

        }
    }

    // Show the dialog if showAddDialog == true
    if (showAddDialog) {
        AddTaskDialog(
            onConfirm = { userInputText ->
                // Insert the user’s text into the DB
                taskViewModel.addTask(userInputText)
            },
            onDismiss = {
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newTaskText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Task") },
        text = {
            TextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                label = { Text("Task Name") }
            )
        },
        confirmButton = {
            // Use the official Material3 Button
            Button(onClick = {
                onConfirm(newTaskText)
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
