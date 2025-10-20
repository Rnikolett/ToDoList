package com.example.todolist.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

@Entity
@TypeConverters({Converter.class})
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String userId;
    private Priority priority;
    private Date creationDate;
    private Date dueDate;
    private Status status;

    // Constructor
    public Task(String title, String description, String userId, Priority priority, Date dueDate) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.priority = priority;
        this.creationDate = Calendar.getInstance().getTime();
        this.dueDate = dueDate;
        this.status = Status.PENDING;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
