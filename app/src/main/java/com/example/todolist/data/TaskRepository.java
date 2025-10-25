package com.example.todolist.data;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    TaskDatabase database;
    private final TaskDao taskDao;
    //private final LiveData<List<Task>> allTasks;
    //private final ExecutorService executorService;

    public TaskRepository(Application application){
        /*TaskDatabase*/ database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        //allTasks = taskDao.getAllTasks();
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
    public LiveData<List<Task>> getAllTasks(String userId) {
        return taskDao.getAllTasks(userId);
    }
    public LiveData<List<Task>> getTasksForToday(String userId) {
        return taskDao.getTasksForToday(userId);
    }

    public LiveData<List<Task>> getTasksForThisWeek(String userId) {
        return taskDao.getTasksForThisWeek(userId);
    }

    public void insertSubTask(SubTask subtask) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertSubTask(subtask);
        });
    }
    public void updateSubTask(SubTask subtask) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.updateSubTask(subtask);
        });
    }
    public void deleteSubTask(SubTask subtask) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteSubTask(subtask);
        });
    }
    public LiveData<List<SubTask>> getSubTasksForTask(int taskId) {
        return taskDao.getSubTasksForTask(taskId);
    }
}
