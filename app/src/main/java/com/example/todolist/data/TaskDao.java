package com.example.todolist.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void  insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM Task WHERE userId = :userId ORDER BY id DESC")
    LiveData<List<Task>> getAllTasks(String userId);

    @Query("SELECT * FROM Task WHERE userId = :userId AND date(dueDate/1000, 'unixepoch') = date('now', 'localtime')")
    LiveData<List<Task>> getTasksForToday(String userId);
    // ('now', 'weekday 1', '-7 days', 'localtime') - Monday of this week
    //('now', 'weekday 0') - Saturday of this week
    @Query("SELECT * FROM Task WHERE userId = :userId AND date(dueDate/1000, 'unixepoch') BETWEEN date('now', 'weekday 1', '-7 days', 'localtime') AND date('now', 'weekday 0', 'localtime')")
    LiveData<List<Task>> getTasksForThisWeek(String userId);

    // --- SUBTASK OPERATIONS ---
    @Insert
    void insertSubTask(SubTask subTask);

    @Update
    void updateSubTask(SubTask subTask);

    @Delete
    void deleteSubTask(SubTask subTask);

    @Query("SELECT * FROM SubTask WHERE taskId = :taskId ORDER BY id ASC")
    LiveData<List<SubTask>> getSubTasksForTask(int taskId);
}
