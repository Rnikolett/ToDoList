package com.example.todolist.data;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, SubTask.class}, version = 4, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static volatile TaskDatabase instance;
    public abstract TaskDao taskDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (TaskDatabase.class){
                instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                TaskDatabase.class,
                                "task_database"
                        )
                        .fallbackToDestructiveMigration(true)
                        .build();
            }
        }
        return instance;
    }
}
