package com.example.logbook.ui.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String IS_COMPLETED = "isCompleted";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + " TEXT, "
            + STATUS + " INTEGER, "
            + IS_COMPLETED + " INTEGER, "
            + DATE + " INTEGER, "
            + TIME + " TEXT)"; // Ensure DATE is defined as INTEGER for Unix timestamps

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        cv.put(IS_COMPLETED, task.isCompleted() ? 1 : 0);
        cv.put(DATE, task.getDate());
        cv.put(TIME, task.getTime());
        db.insert(TODO_TABLE, null, cv);
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        db.beginTransaction();
        Cursor cursor = null;
        try {
            cursor = db.query(TODO_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                    String taskName = cursor.getString(cursor.getColumnIndexOrThrow(TASK));
                    int status = cursor.getInt(cursor.getColumnIndexOrThrow(STATUS));
                    boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(IS_COMPLETED)) == 1;
                    long date = cursor.getLong(cursor.getColumnIndexOrThrow(DATE));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME));

                    Task task = new Task(taskName, id, status, isCompleted, date, time);
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
        }
        return taskList;
    }


    public void updateStatus(int id, int isCompleted) {
        ContentValues cv = new ContentValues();
        cv.put(IS_COMPLETED, isCompleted);
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }


    public void updateTask(int id, String task, long date, String time) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        cv.put(DATE, date);
        cv.put(TIME, time);
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }
}
