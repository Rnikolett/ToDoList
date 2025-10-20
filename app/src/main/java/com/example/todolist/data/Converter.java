package com.example.todolist.data;

import androidx.room.TypeConverter;

import java.util.Date;
//Since Room doesn’t store enums directly, we need a Converter
public class Converter {
    @TypeConverter
    public static String fromPriority(Priority priority) {
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority) {
        return priority == null ? null : Priority.valueOf(priority);
    }

    @TypeConverter
    public static String fromStatus(Status status) {
        return status == null ? null : status.name();
    }

    @TypeConverter
    public static Status toStatus(String status) {
        return status == null ? null : Status.valueOf(status);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
