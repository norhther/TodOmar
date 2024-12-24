package com.example.todomar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todomar.model.TaskRepository
import com.example.todomar.repository.AppDatabase
import todomar.ui.TaskViewModel
import todomar.ui.TaskViewModelFactory
import com.example.todomar.ui.screens.ToDoApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repository = TaskRepository(db.taskDao())
        val viewModelFactory = TaskViewModelFactory(repository)

        setContent {
            // Provide our Compose UI
            val taskViewModel: TaskViewModel = viewModel(factory = viewModelFactory)
            ToDoApp(taskViewModel = taskViewModel)
        }
    }
}
