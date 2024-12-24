package todomar.ui


import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomar.data.Task
import com.example.todomar.model.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

data class TaskUiState(
    val activeTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList()
)

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Expose active and completed tasks as StateFlow
    val uiState: StateFlow<TaskUiState> = repository.getActiveTasks()
        .map { activeTasks ->
            val completedTasks = repository.getCompletedTasks()
            TaskUiState(
                activeTasks = activeTasks,
                completedTasks = emptyList() // We'll combine later
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState())

    val completedTasksFlow: StateFlow<List<Task>> = repository.getCompletedTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String) {
        viewModelScope.launch {
            val dateStr = getFormattedDate() // We'll define getFormattedDate() below
            repository.addTask(title, dateStr)
        }
    }

    private fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            repository.completeTask(task)
        }
    }

    fun deleteAllCompleted() {
        viewModelScope.launch {
            repository.deleteAllCompleted()
        }
    }

    fun uncompleteTask(task: Task) {
        viewModelScope.launch {
            // Set isCompleted = false, clear completionDate or set to null, etc.
            val updatedTask = task.copy(
                isCompleted = false,
            )
            repository.updateTask(updatedTask)
        }
    }

}
