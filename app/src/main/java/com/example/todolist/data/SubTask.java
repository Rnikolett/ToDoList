package com.example.todolist.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Task.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE // delete subtasks when parent task is deleted
        )
)
public class SubTask {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int taskId; // FK linking to parent Task
    private String title;
    private boolean completed;

    public SubTask(int taskId, String title) {
        this.taskId = taskId;
        this.title = title;
        this.completed = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

}
