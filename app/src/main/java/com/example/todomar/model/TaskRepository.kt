package com.example.todomar.model

import kotlinx.coroutines.flow.Flow
import com.example.todomar.data.Task
import com.example.todomar.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    fun getActiveTasks(): Flow<List<Task>> = taskDao.getActiveTasks()
    fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()

    suspend fun addTask(title: String, creationDate: String) {
        val newTask = Task(
            title = title,
            creationDate = creationDate,
            isCompleted = false
        )
        taskDao.insertTask(newTask)
    }

    suspend fun completeTask(task: Task) {
        // Update isCompleted and set completion timestamp
        val updatedTask = task.copy(
            isCompleted = true,
            completionTimestamp = System.currentTimeMillis()
        )
        taskDao.updateTask(updatedTask)
    }

    suspend fun deleteAllCompleted() {
        taskDao.deleteAllCompleted()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

}
