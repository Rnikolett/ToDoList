package com.example.todolist.data;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    TaskDatabase database;
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;
    //private final ExecutorService executorService;

    public TaskRepository(Application application){
        /*TaskDatabase*/ database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
        //executorService = Executors.newSingleThreadExecutor();
    }
    public  void insert(Task task){
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insert(task);
        });
    }
    public  void update(Task task){
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }
    public void delete(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }

    /*public void insert(Task task){
        executorService.execute(() -> taskDao.insert(task));
    }
    public void update(Task task){
        executorService.execute(() -> taskDao.update(task));
    }
    public void delete(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }*/
    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
}
