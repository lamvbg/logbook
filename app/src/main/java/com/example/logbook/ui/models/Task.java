package com.example.logbook.ui.models;

import java.util.Calendar;

public class Task {
    private String task;
    private int id, status;
    private boolean isCompleted;
    private long date;
    private String time;

    // Constructor
    public Task(String task, int id, int status, boolean isCompleted, long date, String time) {
        this.task = task;
        this.id = id;
        this.status = status;
        this.isCompleted = isCompleted;
        this.date = date;
        this.time = time;
    }

    // Getter và Setter cho thuộc tính date
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    // Phương thức kiểm tra xem task có thuộc hôm nay không
    public boolean isTodayTask() {
        Calendar taskDate = Calendar.getInstance();
        taskDate.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        return taskDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                taskDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }

    // Phương thức kiểm tra xem task có thuộc ngày mai không
    public boolean isTomorrowTask() {
        Calendar taskDate = Calendar.getInstance();
        taskDate.setTimeInMillis(date);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);

        return taskDate.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) &&
                taskDate.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR);
    }

    // Getter và Setter khác
    public String getTask() {
        return task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {return status;}

    public void setTask(String task) {
        this.task = task;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
