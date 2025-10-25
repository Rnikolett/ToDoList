package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.data.TaskRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repository;
    private LiveData<List<Task>> userTasks;
    //private final LiveData<List<Task>> allTasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userTasks = repository.getAllTasks(currentUserId);
    }
    public void insert(Task task){
        repository.insert(task);
    }
    public void update(Task task) {
        repository.update(task);
    }
    public void delete(Task task) {
        repository.delete(task);
    }
    public LiveData<List<Task>> getAllTasks() {
        return userTasks;
    }
    public LiveData<List<Task>> getTasksForToday() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return repository.getTasksForToday(userId);
    }

    public LiveData<List<Task>> getTasksForThisWeek() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return repository.getTasksForThisWeek(userId);
    }
    public void insertSubTask(SubTask subTask) { repository.insertSubTask(subTask); }
    public void updateSubTask(SubTask subTask) { repository.updateSubTask(subTask); }
    public void deleteSubTask(SubTask subTask) { repository.deleteSubTask(subTask); }
    public LiveData<List<SubTask>> getSubTasksForTask(int taskId) {
        return repository.getSubTasksForTask(taskId);
    }

}
