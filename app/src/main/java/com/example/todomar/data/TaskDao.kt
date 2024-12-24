package com.example.todomar.data

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    // INSERT ----------------------------------------------------------------
    // A suspend insert can return:
    //  - Long (the new row ID), or
    //  - List<Long> for multiple inserts, or
    //  - Unit if you don't need a return value.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long  // or 'suspend fun insertTask(task: Task)'

    // UPDATE ----------------------------------------------------------------
    // A suspend update typically returns an Int representing
    // how many rows were updated, or Unit if you don't care.
    @Update
    suspend fun updateTask(task: Task): Int



    // DELETE ----------------------------------------------------------------
    // A suspend delete typically returns an Int representing
    // how many rows were deleted, or Unit if you don't care.
    @Delete
    suspend fun deleteTask(task: Task): Int

    // CUSTOM QUERY (DELETE ALL) --------------------------------------------
    // DELETE queries must return void or int (# of rows deleted).
    @Query("DELETE FROM tasks WHERE isCompleted = 1")
    suspend fun deleteAllCompleted(): Int

    // SELECT QUERIES -------------------------------------------------------
    // For queries, you can return Flow<List<Task>> or just List<Task>.
    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    fun getActiveTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1")
    fun getCompletedTasks(): Flow<List<Task>>
}