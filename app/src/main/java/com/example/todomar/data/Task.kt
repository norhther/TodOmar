package com.example.todomar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val creationTimestamp: Long = System.currentTimeMillis(),
    val completionTimestamp: Long? = null,
    val creationDate: String
)